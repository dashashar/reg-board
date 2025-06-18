document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');
    if (!form) return;

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const eventId = document.getElementById('eventId').value;
        if (!eventId) {
            console.error('id мероприятия отсутствует');
            return;
        }

        const formData = collectFormData();
        const csrfToken = document.querySelector('input[name="_csrf"]').value;

        submitRegistration(eventId, formData, csrfToken)
            .then(handleResponse)
            .catch(handleError);
    });
});

function collectFormData() {
    const formData = [];
    const checkboxGroups = {};

    document.querySelectorAll('input[type="checkbox"][name^="field_"]').forEach(checkbox => {
        const fieldName = checkbox.name;
        if (!checkboxGroups[fieldName]) {
            checkboxGroups[fieldName] = [];
        }
        if (checkbox.checked) {
            checkboxGroups[fieldName].push(checkbox.value);
        }
    });

    document.querySelectorAll('[name^="field_"]').forEach(field => {
        const fieldName = field.name;

        if (field.type === 'checkbox') return;

        if (field.type === 'radio') {
            if (field.checked) {
                addFieldData(formData, fieldName, field.value);
            }
        } else {
            addFieldData(formData, fieldName, field.value);
        }
    });

    Object.entries(checkboxGroups).forEach(([fieldName, values]) => {
        if (values.length > 0) {
            const combinedValue = values.join(', ');
            addFieldData(formData, fieldName, combinedValue);
        }
    });

    return formData;
}

function addFieldData(formDataArray, fieldName, value) {
    if (value === null || value === undefined || value === '') {
        return;
    }
    const fieldId = fieldName.replace('field_', '');
    formDataArray.push({
        fieldId: fieldId,
        answerValue: value
    });
}

async function submitRegistration(eventId, formData, csrfToken) {
    const response = await fetch(`/registration/event/${eventId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(formData)
    });

    const contentType = response.headers.get('content-type');
    if (!contentType) return response;

    if (contentType.includes('text/html')) {
        return {
            html: await response.text(),
            redirectUrl: response.url
        };
    }

    return response;
}

function handleResponse(response) {
    if (response.redirectUrl) {
        window.location.href = response.redirectUrl;
        return;
    }

    if (response.html) {
        document.open();
        document.write(response.html);
        document.close();
        return;
    }

    if (response.message) {
        alert(response.message);
    }

    console.log('Успешная регистрация', response);
}

function handleError(error) {
    console.error('Ошибка при регистрации:', error);
    alert('Произошла ошибка при регистрации. Пожалуйста, попробуйте еще раз.');
}
const FIELD_TYPES = {
    SHORT_QUESTION: 'SHORT_QUESTION',
    LONG_QUESTION: 'LONG_QUESTION',
    SINGLE_CHOICE: 'SINGLE_CHOICE',
    MULTIPLE_CHOICE: 'MULTIPLE_CHOICE'
};

function createField(fieldData = {}) {
    const fieldContainer = document.createElement('div');
    fieldContainer.className = 'field-container';

    fieldContainer.innerHTML += `
        <div class="row">
            <div class="col-md-4">
                <select class="form-select field-type" ${fieldData.id ? 'disabled' : ''} required>
                    <option value="${FIELD_TYPES.SHORT_QUESTION}" ${fieldData.fieldType === FIELD_TYPES.SHORT_QUESTION ? 'selected' : ''}>Короткий ответ</option>
                    <option value="${FIELD_TYPES.LONG_QUESTION}" ${fieldData.fieldType === FIELD_TYPES.LONG_QUESTION ? 'selected' : ''}>Длинный ответ</option>
                    <option value="${FIELD_TYPES.SINGLE_CHOICE}" ${fieldData.fieldType === FIELD_TYPES.SINGLE_CHOICE ? 'selected' : ''}>Выбор одного варианта</option>
                    <option value="${FIELD_TYPES.MULTIPLE_CHOICE}" ${fieldData.fieldType === FIELD_TYPES.MULTIPLE_CHOICE ? 'selected' : ''}>Выбор нескольких вариантов</option>
                </select>
            </div>
            <div class="col-md-6">
                <input type="text" class="form-control question" placeholder="Текст вопроса" 
                       value="${fieldData.question || ''}">
            </div>
            <div class="col-md-2">
                <div class="form-check">
                    <input class="form-check-input is-required" type="checkbox" 
                           ${fieldData.isRequired ? 'checked' : ''}>
                    <label class="form-check-label">Обязательный вопрос</label>
                </div>
                <button type="button" class="btn btn-danger btn-sm remove-field">Удалить</button>
            </div>
        </div>
        <div class="options-container ${fieldData.fieldType === FIELD_TYPES.SINGLE_CHOICE || fieldData.fieldType === FIELD_TYPES.MULTIPLE_CHOICE ? '' : 'd-none'}">
            <div class="mb-2">
                <button type="button" class="btn btn-secondary btn-sm add-option">Добавить вариант</button>
            </div>
            <div class="options-list">
                ${fieldData.options ? fieldData.options.map(option => `
                    <div class="input-group mb-2">
                        <input type="text" class="form-control option-value" 
                               placeholder="Текст варианта" value="${option}">
                        <button class="btn btn-outline-danger remove-option" type="button">Удалить</button>
                    </div>
                `).join('') : ''}
            </div>
        </div>
    `;

    fieldContainer.querySelector('.field-type').addEventListener('change', handleFieldTypeChange);
    fieldContainer.querySelector('.remove-field').addEventListener('click', () => fieldContainer.remove());
    fieldContainer.querySelector('.add-option').addEventListener('click', (e) => addOption(e.target.closest('.field-container')));

    return fieldContainer;
}

function handleFieldTypeChange(event) {
    const fieldContainer = event.target.closest('.field-container');
    const optionsContainer = fieldContainer.querySelector('.options-container');
    const fieldType = event.target.value;

    if (fieldType === FIELD_TYPES.SINGLE_CHOICE || fieldType === FIELD_TYPES.MULTIPLE_CHOICE) {
        optionsContainer.classList.remove('d-none');
        if (fieldContainer.querySelectorAll('.option-value').length === 0) {
            addOption(fieldContainer);
        }
    } else {
        optionsContainer.classList.add('d-none');
    }
}

function addOption(fieldContainer) {
    const optionsList = fieldContainer.querySelector('.options-list');
    const optionDiv = document.createElement('div');
    optionDiv.className = 'input-group mb-2';
    optionDiv.innerHTML = `
        <input type="text" class="form-control option-value" placeholder="Текст варианта">
        <button class="btn btn-outline-danger remove-option" type="button">Удалить</button>
    `;

    optionDiv.querySelector('.remove-option').addEventListener('click', () => {
        if (optionsList.querySelectorAll('.input-group').length > 1) {
            optionDiv.remove();
        }
    });
    optionsList.appendChild(optionDiv);
}

function validateForm() {
    let isValid = true;

    document.querySelectorAll('.invalid-feedback').forEach(el => el.remove());
    document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

    document.querySelectorAll('.field-container').forEach(container => {
        const questionInput = container.querySelector('.question');
        const fieldTypeSelect = container.querySelector('.field-type');

        if (!questionInput.value.trim()) {
            showError(questionInput, 'Пожалуйста, введите текст вопроса');
            isValid = false;
        }

        if (!fieldTypeSelect.value) {
            showError(fieldTypeSelect, 'Пожалуйста, выберите тип вопроса');
            isValid = false;
        }

        if (fieldTypeSelect.value === FIELD_TYPES.SINGLE_CHOICE ||
            fieldTypeSelect.value === FIELD_TYPES.MULTIPLE_CHOICE) {
            const options = container.querySelectorAll('.option-value');
            let hasEmptyOptions = false;

            options.forEach(opt => {
                if (!opt.value.trim()) {
                    showError(opt, 'Пожалуйста, введите текст варианта');
                    hasEmptyOptions = true;
                    isValid = false;
                }
            });

            if (options.length === 0) {
                const addButton = container.querySelector('.add-option');
                showError(addButton, 'Добавьте хотя бы один вариант ответа');
                isValid = false;
            }
        }
    });

    return isValid;
}

function showError(inputElement, message) {
    inputElement.classList.add('is-invalid');

    const errorDiv = document.createElement('div');
    errorDiv.className = 'invalid-feedback';
    errorDiv.textContent = message;

    inputElement.parentNode.appendChild(errorDiv);
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function submitFormData(url, formFields) {
    const csrfToken = getCookie('XSRF-TOKEN');
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(formFields)
    })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else if (!response.ok) {
                return response.text().then(html => {
                    document.open();
                    document.write(html);
                    document.close();
                });
            } else {
                window.location.reload();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Ошибка при сохранении формы. Попробуйте позже еще раз');
        });
}
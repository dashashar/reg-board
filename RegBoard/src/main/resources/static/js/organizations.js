function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function showMessage(message, type) {
    const $container = $('#messageContainer');
    const $alert = $(`<div class="alert alert-${type} alert-dismissible fade show" role="alert">
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>`);

    $container.empty().append($alert);

    setTimeout(() => {
        $alert.alert('close');
    }, 5000);
}

function showError(message) {
    showMessage(message, 'danger');
}

function showSuccess(message) {
    showMessage(message, 'success');
}

function initOrganizationsPage() {
    loadOrganizations();

    $('#createOrgBtn').click(function() {
        $('#createOrgForm').show();
        $('#createOrgBtn').hide();
    });

    $('#cancelCreateBtn').click(function() {
        $('#createOrgForm').hide();
        $('#createOrgBtn').show();
        $('#formErrors').empty().hide();
        $('#orgForm')[0].reset();
    });

    $('#orgForm').submit(function(e) {
        e.preventDefault();
        createOrganization();
    });
}

function loadOrganizations() {
    $.ajax({
        url: `/api/org/account`,
        type: 'GET',
        success: renderOrganizations,
        error: function(xhr) {
            showError('Ошибка при загрузке организаций: ' + (xhr.responseJSON?.error || 'Неизвестная ошибка'));
        }
    });
}

function renderOrganizations(organizations) {
    const $orgList = $('#orgList');
    $orgList.empty();

    if (organizations.length === 0) {
        $orgList.append('<div class="alert alert-info">У вас пока нет организаций</div>');
        return;
    }

    organizations.forEach(org => {
        const $orgCard = $(`
            <div class="org-card card mb-3">
                <div class="card-body">
                    <h3 class="card-title">${org.name}</h3>
                    <div class="org-actions">
                        <button onclick="editOrganization(${org.id})" class="btn btn-warning text-white btn-sm">Редактировать</button>
                        <button onclick="viewEvents(${org.id})" class="btn btn-primary btn-sm">Мероприятия</button>
                        <button onclick="deleteOrganization(${org.id})" class="btn btn-danger btn-sm">Удалить</button>
                    </div>
                </div>
            </div>
        `);
        $orgList.append($orgCard);
    });
}

function createOrganization() {
    const formData = {
        name: $('input[name="name"]').val(),
        description: $('textarea[name="description"]').val(),
        email: $('input[name="email"]').val()
    };

    $.ajax({
        url: `/api/org/account`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function() {
            $('#createOrgForm').hide();
            $('#createOrgBtn').show();
            $('#orgForm')[0].reset();
            loadOrganizations();
            showSuccess('Организация успешно создана');
        },
        error: function(xhr) {
            const $errorDiv = $('#formErrors');
            if (xhr.status === 400 && xhr.responseJSON?.details) {
                let errors = '';
                for (const [field, message] of Object.entries(xhr.responseJSON.details)) {
                    errors += `${field}: ${message}<br>`;
                }
                $errorDiv.html(errors).show();
            } else {
                $errorDiv.html(xhr.responseJSON?.error || 'Ошибка при создании организации').show();
            }
        }
    });
}

function deleteOrganization(orgId) {
    if (!confirm('Вы уверены, что хотите удалить организацию?')) return;

    $(`button[onclick="deleteOrganization(${orgId})"]`)
        .prop('disabled', true)
        .text('Удаление...');

    $.ajax({
        url: `/api/org/${orgId}`,
        type: 'DELETE',
        success: function() {
            $(`button[onclick="deleteOrganization(${orgId})"]`)
                .closest('.org-card')
                .fadeOut(300, function() { $(this).remove(); });
            showSuccess('Организация успешно удалена');
        },
        error: function(xhr) {
            $(`button[onclick="deleteOrganization(${orgId})"]`)
                .prop('disabled', false)
                .text('Удалить');

            showError('Ошибка при удалении организации: ' +
                (xhr.responseJSON?.error || xhr.statusText || 'Неизвестная ошибка'));
        }
    });
}

function editOrganization(orgId) {
    window.location.href = `/org/${orgId}`;
}

function viewEvents(orgId) {
    window.location.href = `/org/${orgId}/event`;
}

function initOrganizationEditPage() {
    loadOrganization();

    $('#editOrgForm').submit(function(e) {
        e.preventDefault();
        updateOrganization();
    });

    $('#addAdminForm').submit(function(e) {
        e.preventDefault();
        addAdmin();
    });
}

function loadOrganization() {
    $.ajax({
        url: `/api/org/${orgId}`,
        type: 'GET',
        success: function(org) {
            $('#orgName').val(org.name);
            $('#orgDescription').val(org.description);
            $('#orgEmail').val(org.email);
            renderAdmins(org.admins);
        },
        error: function(xhr) {
            showError('Ошибка при загрузке организации: ' + (xhr.responseJSON?.error || 'Неизвестная ошибка'));
        }
    });
}

function renderAdmins(admins) {
    const $adminsList = $('#adminsList');
    $adminsList.empty();

    admins.forEach(admin => {
        const $adminItem = $(`
            <div class="admin-item">
                <span>${admin.firstName} ${admin.lastName} (${admin.email})</span>
                <button onclick="deleteAdmin(${admin.id})" class="btn btn-danger btn-sm">Удалить</button>
            </div>
        `);
        $adminsList.append($adminItem);
    });
}

function updateOrganization() {
    const formData = {
        name: $('#orgName').val(),
        description: $('#orgDescription').val(),
        email: $('#orgEmail').val()
    };

    $.ajax({
        url: `/api/org/${orgId}`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function() {
            showSuccess('Изменения успешно сохранены');
        },
        error: function(xhr) {
            const $errorDiv = $('#formErrors');
            if (xhr.status === 400 && xhr.responseJSON?.details) {
                let errors = '';
                for (const [field, message] of Object.entries(xhr.responseJSON.details)) {
                    errors += `${field}: ${message}<br>`;
                }
                $errorDiv.html(errors).show();
            } else {
                $errorDiv.html(xhr.responseJSON?.error || 'Ошибка при обновлении организации').show();
            }
        }
    });
}

function addAdmin() {
    const email = $('input[name="email"]', '#addAdminForm').val();

    $.ajax({
        url: `/api/org/${orgId}`,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ email: email }),
        success: function() {
            loadOrganization();
            $('#addAdminForm')[0].reset();
            $('#adminFormErrors').empty().hide();
            showSuccess('Администратор успешно добавлен');
        },
        error: function(xhr) {
            const $errorDiv = $('#adminFormErrors');
            $errorDiv.html(xhr.responseJSON?.error || 'Ошибка при добавлении администратора').show();
        }
    });
}

function deleteAdmin(accountId) {
    if (!confirm('Вы уверены, что хотите удалить администратора?')) return;

    $.ajax({
        url: `/api/org/${orgId}/account/${accountId}`,
        type: 'DELETE',
        success: function() {
            loadOrganization();
            showSuccess('Администратор успешно удален');
        },
        error: function(xhr) {
            showError('Ошибка при удалении администратора: ' + (xhr.responseJSON?.error || 'Неизвестная ошибка'));
        }
    });
}

$(document).ready(function() {
    initOrganizationsPage();
});

$(document).ready(function() {
    if (typeof orgId !== 'undefined') {
        initOrganizationEditPage();
    }
});
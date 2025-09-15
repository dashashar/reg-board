<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>

<#macro head>
    <style>
        .admin-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
    </style>
</#macro>
<#macro header>
    <@h.base links=[
    {"url": "/event", "text": "К мероприятиям"},
    {"url": "/org/account", "text": "Все организации"},
    {"url": "/org/${orgId}/event", "text": "Мероприятия организации"},
    {"url": "/org/${orgId}", "text": "Редактировать организацию"},
    {"url": "/account/logout", "text": "Выйти"}
    ]/>
</#macro>
<#macro content>
    <h1 class="mb-4">Редактирование организации</h1>

    <div id="orgInfo" class="card mb-4 p-3">
        <form id="editOrgForm">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="mb-3">
                <label class="form-label">Название:</label>
                <input type="text" name="name" id="orgName" class="form-control" required maxlength="255">
            </div>
            <div class="mb-3">
                <label class="form-label">Описание:</label>
                <textarea name="description" id="orgDescription" class="form-control" required maxlength="2000"></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Email:</label>
                <input type="email" name="email" id="orgEmail" class="form-control" required maxlength="255">
            </div>
            <button type="submit" class="btn btn-primary">Сохранить изменения</button>
        </form>
        <div id="formErrors" class="alert alert-danger mt-3" style="display: none;"></div>
    </div>

    <div class="admin-list card p-3">
        <h2 class="mb-3">Администраторы</h2>
        <div id="adminsList" class="mb-4"></div>

        <h3 class="mb-3">Добавить администратора</h3>
        <form id="addAdminForm" class="mb-3">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="input-group">
                <input type="email" name="email" class="form-control" placeholder="Email" required>
                <button type="submit" class="btn btn-success">Добавить</button>
            </div>
        </form>
        <div id="adminFormErrors" class="alert alert-danger" style="display: none;"></div>
    </div>

    <div id="messageContainer"></div>
</#macro>

<#macro scripts>
    <script>const orgId = ${orgId};</script>
    <script src="/static/js/organizations.js"></script>
</#macro>

<@base.page
title="Редактирование организации"
head=head
header=header
content=content
scripts=scripts/>

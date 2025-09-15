<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>

<#macro head>
    <style>
        .org-card {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .org-actions {
            margin-top: 10px;
        }
        .btn-orange {
            background-color: #154E89;
            border-color: #154E89;
            color: #fff;
            font-weight: 600;
        }
        .btn-orange:hover, .btn-orange:focus {
            background-color: #154E89;
            border-color: #154E89;
            color: #fff;
        }
    </style>
</#macro>
<#macro header>
    <@h.base links=[
    {"url": "/event", "text": "К мероприятиям"},
    {"url": "/org/account", "text": "Все организации"},
    {"url": "/account/logout", "text": "Выйти"}
    ]/>
</#macro>
<#macro content>
    <h1 class="mb-4">Мои организации</h1>

    <button id="createOrgBtn" class="btn btn-orange mb-4">Создать новую организацию</button>

    <div id="createOrgForm" style="display: none;" class="card mb-4 p-3">
        <h3 class="mb-3">Создать организацию</h3>
        <form id="orgForm">
            <div class="mb-3">
                <label class="form-label">Название:</label>
                <input type="text" name="name" class="form-control" required maxlength="255">
            </div>
            <div class="mb-3">
                <label class="form-label">Описание:</label>
                <textarea name="description" class="form-control" required maxlength="2000"></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Email:</label>
                <input type="email" name="email" class="form-control" required maxlength="255">
            </div>
            <button type="submit" class="btn btn-orange">Создать</button>
            <button type="button" id="cancelCreateBtn" class="btn btn-secondary">Отмена</button>
        </form>
        <div id="formErrors" class="alert alert-danger mt-3" style="display: none;"></div>
    </div>

    <div id="orgList" class="mb-4"></div>

    <div id="messageContainer"></div>
</#macro>

<#macro scripts>
    <script src="/static/js/organizations.js"></script>
</#macro>

<@base.page
title="Мои организации"
head=head
header=header
content=content
scripts=scripts/>
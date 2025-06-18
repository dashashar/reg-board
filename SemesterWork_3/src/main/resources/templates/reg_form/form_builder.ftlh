<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>
<#macro head>
    <style>
        .field-container {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 5px;
        }
        .options-container {
            margin-top: 10px;
        }
        .is-invalid {
            border-color: #dc3545;
        }
        .invalid-feedback {
            width: 100%;
            margin-top: 0.25rem;
            font-size: 0.875em;
            color: #dc3545;
        }
        .main-content {
            flex: 1;
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
    <h2>Формы регистрации</h2>
    <div class="mb-3">
        <button type="button" class="btn btn-primary" id="addField">Добавить вопрос</button>
    </div>

    <form id="formBuilder" method="POST" action="/org/${orgId}/event/${eventId}/form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div id="fieldsContainer">
            <#if fields?? && fields?has_content>
                <#list fields as field>
                    <div class="field-container">
                        <input type="hidden" class="field-id" value="${field.id()}">
                        <input type="hidden" class="original-question" value="${field.question()}">
                        <input type="hidden" class="original-is-required" value="${field.isRequired()?c}">
                        <input type="hidden" class="original-options" value="${field.options()!''}">
                        <input type="hidden" class="original-position" value="${field.position()}">
                        <div class="row">
                            <div class="col-md-4">
                                <select class="form-select field-type" disabled>
                                    <option value="SHORT_QUESTION" <#if field.fieldType() == 'SHORT_QUESTION'>selected</#if>>Короткий ответ</option>
                                    <option value="LONG_QUESTION" <#if field.fieldType() == 'LONG_QUESTION'>selected</#if>>Длинный ответ</option>
                                    <option value="SINGLE_CHOICE" <#if field.fieldType() == 'SINGLE_CHOICE'>selected</#if>>Выбор одного варианта</option>
                                    <option value="MULTIPLE_CHOICE" <#if field.fieldType() == 'MULTIPLE_CHOICE'>selected</#if>>Выбор нескольких вариантов</option>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control question" placeholder="Текст вопроса"
                                       value="${field.question()}">
                            </div>
                            <div class="col-md-2">
                                <div class="form-check">
                                    <input class="form-check-input is-required" type="checkbox"
                                           <#if field.isRequired()>checked</#if>>
                                    <label class="form-check-label">Обязательный вопрос</label>
                                </div>
                                <button type="button" class="btn btn-danger btn-sm remove-field">Удалить</button>
                            </div>
                        </div>
                        <div class="options-container <#if field.fieldType() != 'SINGLE_CHOICE' && field.fieldType() != 'MULTIPLE_CHOICE'>d-none</#if>">
                            <div class="mb-2">
                                <button type="button" class="btn btn-secondary btn-sm add-option">Добавить вариант</button>
                            </div>
                            <div class="options-list">
                                <#if field.options()??>
                                    <#list field.options()?eval as option>
                                        <div class="input-group mb-2">
                                            <input type="text" class="form-control option-value"
                                                   placeholder="Текст варианта" value="${option}">
                                            <button class="btn btn-outline-danger remove-option" type="button">Удалить</button>
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="alert alert-info">Нет сохраненных вопросов. Добавьте новые вопросы</div>
            </#if>
        </div>
        <button type="submit" class="btn btn-success mt-3">Сохранить</button>
    </form>
</#macro>

<#macro scripts>
    <script src="/static/js/form_common.js"></script>
    <script src="/static/js/form_edit.js"></script>
</#macro>

<@base.page
title="Форма регистрации"
head=head
header=header
content=content
scripts=scripts
/>
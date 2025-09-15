<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>
<#macro head>
    <style>
        .has-error label {
            color: #dc3545;
        }
    </style>
</#macro>
<#macro header>
    <@h.base links=[
    {"url": "/event", "text": "Все мероприятия"},
    {"url": "/account/registration", "text": "Мои мероприятия"},
    {"url": "/org/account", "text": "Организаторам"},
    {"url": "/account/logout", "text": "Выйти"}
    ]/>
</#macro>
<#macro content>
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <#if details.imgId??>
                <img src="/api/image/${details.imgId}" class="card-img-top event-img" alt="${details.title}">
            <#else>
                <img src="https://via.placeholder.com/300x200" class="card-img-top event-img" alt="Placeholder">
            </#if>
            <h1 class="mb-4">${details.title!''}</h1>

            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Организатор: ${details.org.name()!''}</h5>
                    <p class="card-text">${details.org.description()!''}</p>
                    <p class="card-text">Контакты: ${details.org.email()!''}</a></p>
                </div>
            </div>

            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">О мероприятии</h5>
                    <p class="card-text">${details.description!''}</p>
                    <p class="card-text">
                        <strong>Дата и время:</strong>
                        ${details.timeStart} -
                        ${details.timeEnd}
                    </p>
                    <p class="card-text">
                        <strong>Место проведения:</strong> ${details.address!''}, ${details.city!''}
                    </p>

                    <#if details.ticket?? && details.ticket.totalTickets()??>
                        <#if details.registrationIsOpen>
                            <p class="card-text">
                                <strong>Билетов доступно:</strong>
                                ${details.ticket.totalTickets() - details.ticket.soldTickets()!0} из ${details.ticket.totalTickets()}
                            </p>
                        </#if>
                        <#if details.ticket.price()?? && details.ticket.price() gt 0>
                            <p class="card-text"><strong>Цена:</strong> ${details.ticket.price()} руб.</p>
                        </#if>
                    </#if>
                </div>
            </div>

            <#if details.accountHasReg>
                <div class="alert alert-info" role="alert">
                    Вы зарегистрированы на это мероприятие
                </div>
                <#if details.registrationId??>
                    <img src="/api/image/${details.registrationId}" alt="qr-code">
                    <p>При посещении мероприятия покажите этот qr-код организаторам</p>
                </#if>
            <#elseif details.registrationIsOpen>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Форма регистрации</h5>
                        <form id="registrationForm" action="/registration/event/${eventId}" method="post">
                            <input type="hidden" id="eventId" name="eventId" value="${eventId}">
                            <#list details.fields as field>
                                <div class="mb-3">
                                    <label for="field_${field.id()}" class="form-label">
                                        ${field.question()!}
                                        <#if field.isRequired()!false><span class="text-danger">*</span></#if>
                                    </label>

                                    <#switch field.fieldType()!>
                                        <#case "SHORT_QUESTION">
                                            <input type="text" class="form-control" id="field_${field.id()}"
                                                   name="field_${field.id()}"
                                                   <#if field.isRequired()!false>required</#if>>
                                            <#break>

                                        <#case "LONG_QUESTION">
                                            <textarea class="form-control" id="field_${field.id()}"
                                                      name="field_${field.id()}" rows="3"
                                                      <#if field.isRequired()!false>required</#if>></textarea>
                                            <#break>

                                        <#case "SINGLE_CHOICE">
                                            <#if field.options()??>
                                                <#assign options = field.options()?eval>
                                                <#list options as option>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="radio"
                                                               name="field_${field.id()}" id="field_${field.id()}_${option?index}"
                                                               value="${option}"
                                                               <#if field.isRequired()!false>required</#if>>
                                                        <label class="form-check-label" for="field_${field.id()}_${option?index}">
                                                            ${option}
                                                        </label>
                                                    </div>
                                                </#list>
                                            </#if>
                                            <#break>

                                        <#case "MULTIPLE_CHOICE">
                                            <#if field.options()??>
                                                <#assign options = field.options()?eval>
                                                <#list options as option>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox"
                                                               name="field_${field.id()}" id="field_${field.id()}_${option?index}"
                                                               value="${option}">
                                                        <label class="form-check-label" for="field_${field.id()}_${option?index}">
                                                            ${option}
                                                        </label>
                                                    </div>
                                                </#list>
                                            </#if>
                                            <#break>
                                    </#switch>
                                </div>
                            </#list>

                            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                        </form>
                    </div>
                </div>
            <#else>
                <div class="alert alert-warning" role="alert">
                    Регистрация на это мероприятие закрыта
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro scripts>
    <script src="/static/js/registration.js"></script>
</#macro>

<@base.page
title='${details.title}'
head=head
header=header
content=content
scripts=scripts/>
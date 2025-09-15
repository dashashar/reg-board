<#import "base.ftlh" as base>
<#import "/header.ftlh" as h>
<#macro head></#macro>
<#macro header>
    <@h.base links=[
    {"url": "/event", "text": "Все мероприятия"},
    {"url": "/account/registration", "text": "Мои мероприятия"},
    {"url": "/org/account", "text": "Организаторам"},
    {"url": "/account/logout", "text": "Выйти"}
    ]/>
</#macro>
<#macro content>
    <div class="alert alert-danger" role="alert">


        <#if message??>
            <h4 class="alert-heading">${message}</h4>
        <#elseif errors?? && errors?size gt 0>
            <ul class="mb-0">
                <#list errors as field, error>
                    <li><strong>${field}:</strong> ${error}</li>
                </#list>
            </ul>
        <#else>
            <p>Неизвестная ошибка</p>
        </#if>

        <hr>
        <p class="mb-0">Попробуйте повторить действие позже</p>
    </div>

    <div class="text-center mt-3">
        <a href="/event" class="btn btn-primary">Вернуться на главную</a>
    </div>
</#macro>
<#macro scripts></#macro>

<@base.page
title="Ошибка"
head=head
header=header
content=content
scripts=scripts
/>
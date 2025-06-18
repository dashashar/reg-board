<#import "/base.ftlh" as base>
<#import "ticket_base.ftlh" as ticket_form>
<#import "/header.ftlh" as h>
<#macro head>

</#macro>

<#macro content>
    <@ticket_form.ticket_form
    action_url="/org/${orgId}/event/${eventId}/ticket/create"
    title_text="Создание билета"
    button_text="Далее: Регистрация"
    />
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
<#macro scripts>
    <script>
        document.getElementById('form').addEventListener('submit', function(e) {
            const startDate = document.getElementById('timeStart').value;
            const endDate = document.getElementById('timeEnd').value;
            const errorElement = document.getElementById('dateError');

            if (startDate && endDate && new Date(startDate) >= new Date(endDate)) {
                e.preventDefault();
                errorElement.style.display = 'block';
            } else {
                errorElement.style.display = 'none';
            }
        });
    </script>
</#macro>

<@base.page
title="Создание билета"
head=head
header=header
content=content
scripts=scripts
/>
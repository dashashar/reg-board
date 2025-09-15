<#import "/base.ftlh" as base>
<#import "ticket_base.ftlh" as ticket_form>
<#import "/header.ftlh" as h>
<#macro head>
    <style>
        .content-wrapper {
            display: flex;
            gap: 20px;
        }
        .main-content {
            flex: 1;
        }
    </style>
</#macro>

<#macro content>
    <div class="content-wrapper">
        <div class="main-content">
            <@ticket_form.ticket_form
            action_url="/org/${orgId}/ticket/${ticketId}/update"
            title_text="Редактирование билета"
            button_text="Сохранить изменения"
            />
        </div>
    </div>
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
title="Редактирование билета"
head=head
header=header
content=content
scripts=scripts
/>
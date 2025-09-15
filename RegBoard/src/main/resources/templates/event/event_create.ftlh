<#import "/base.ftlh" as base>
<#import "event_base.ftlh" as event_form>
<#import "/header.ftlh" as h>
<#macro head>
    <style>
        #suggestions {
            position: absolute;
            z-index: 1000;
            background: white;
            border: 1px solid #ccc;
            max-height: 200px;
            overflow-y: auto;
            width: 100%;
            box-sizing: border-box;
            display: none;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        #suggestions div {
            padding: 8px;
            cursor: pointer;
        }
        #suggestions div:hover {
            background-color: #f0f0f0;
        }
        .city-container {
            position: relative;
            width: 300px;
        }
        #cityInput {
            width: 100%;
            box-sizing: border-box;
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
    <@event_form.event_form
    action_url="/org/${orgId}/event/create"
    title_text="Создание мероприятия"
    button_text="Далее: Билет"
    />
</#macro>

<#macro scripts>
    <script src="/static/js/get_cities_geonames.js"></script>
    <script src="/static/js/save_event.js"></script>
</#macro>

<@base.page
title="Создание мероприятия"
head=head
header=header
content=content
scripts=scripts
/>
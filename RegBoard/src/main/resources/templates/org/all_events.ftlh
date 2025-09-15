<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>

<#macro head>
    <style>
        .event-card {
            width: 300px;
            margin: 15px;
            display: inline-block;
            vertical-align: top;
        }
        .event-card img {
            height: 200px;
            object-fit: cover;
        }
        .event-actions .btn {
            width: 100%;
        }
        .btn-orange-outline {
            border: 2px solid #154E89;
            color: #154E89;
            font-weight: 700;
            background: #fff;
        }
        .btn-orange-outline:hover, .btn-orange-outline:focus {
            background: #154E89;
            color: #fff;
            border-color: #154E89;
        }
        .btn-bold {
            font-weight: 700;
        }
        .event-img-square {
            aspect-ratio: 1 / 1;
            object-fit: cover;
            width: 100%;
            border-radius: 10px 10px 0 0;
        }
        .event-card {
            border-radius: 10px;
            overflow: hidden;
            height: 100%;
            display: flex;
            flex-direction: column;
        }
        .card-body {
            flex: 1 1 auto;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
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
    <div class="mb-4">
        <a href="/org/${orgId}/event/create" class="btn btn-primary">Создать мероприятие</a>
    </div>

    <div class="container">
        <div class="row g-4">
            <#if events?? && events?has_content>
                <#list events as event>
                    <div class="col-12 col-sm-6 col-lg-4 d-flex">
                        <div class="card event-card w-100">
                            <#if event.imgId()??>
                                <img src="/api/image/${event.imgId()}" class="event-img-square" alt="${event.title()}">
                            <#else>
                                <img src="https://via.placeholder.com/300x300" class="event-img-square"
                                     alt="Placeholder">
                            </#if>
                            <div class="card-body">
                                <h5 class="card-title">${event.title()}</h5>
                                <p class="card-text">
                                    ${event.address()}, ${event.city()}<br>
                                    ${event.timeStart()} - ${event.timeEnd()}<br>
                                    <#if event.price()??>Цена: ${event.price()} руб.</#if>
                                </p>
                                <div class="d-flex flex-column gap-2">
                                    <a href="/org/${orgId}/event/${event.id()}/update"
                                       class="btn btn-sm btn-orange-outline">
                                        Событие
                                    </a>
                                    <#if event.ticketId()??>
                                        <a href="/org/${orgId}/ticket/${event.ticketId()}/update"
                                           class="btn btn-sm btn-orange-outline">
                                            Билет
                                        </a>
                                    <#else>
                                        <a href="/org/${orgId}/event/${event.id()}/ticket/create"
                                           class="btn btn-sm btn-orange-outline">
                                            Создать билет
                                        </a>
                                    </#if>
                                    <a href="/org/${orgId}/event/${event.id()}/form"
                                       class="btn btn-sm btn-orange-outline">
                                        Форма регистрации
                                    </a>
                                    <a href="/org/${orgId}/event/${event.id()}/registration"
                                       class="btn btn-sm btn-outline-info btn-bold">
                                        Список участников
                                    </a>
                                    <button onclick="deleteEvent('${orgId}', '${event.id()}')"
                                            class="btn btn-sm btn-outline-danger btn-bold">
                                        Удалить
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="alert alert-info" style="width: 100%;">
                    Нет созданных мероприятий
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro scripts>
    <script>
        function deleteEvent(orgId, eventId) {
            if (confirm("Вы точно хотите удалить мероприятие?")) {
                fetch('/org/' + orgId + '/event/' + eventId + '/delete', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    }
                })
                    .then(response => {
                        if (response.redirected) {
                            window.location.href = response.url;
                        }
                    })
                    .catch(error => {
                        alert("Ошибка при удалении");
                    });
            }
        }
    </script>
</#macro>

<@base.page
title="События организации"
head=head
header=header
content=content
scripts=scripts/>
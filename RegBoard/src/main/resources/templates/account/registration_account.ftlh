<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>
<#macro head>
    <style>
        .event-card {
            transition: transform 0.2s;
            margin-bottom: 20px;
            cursor: pointer;
        }
        .event-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }
        .event-img {
            height: 200px;
            object-fit: cover;
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
    <h1 class="mb-4">Мои мероприятия</h1>

    <#if events?size == 0>
        <div class="alert alert-info">
            Вы пока не зарегистрированы ни на одно мероприятие.
        </div>
    <#else>
        <div class="row">
            <#list events as event>
                <div class="col-md-6 col-lg-4">
                    <div class="card event-card" onclick="window.location.href='/event/${event.id()}'">
                        <#if event.imgId()??>
                            <img src="/api/image/${event.imgId()}" class="card-img-top event-img" alt="${event.title()}">
                        <#else>
                            <img src="https://via.placeholder.com/300x200?text=No+Image" class="card-img-top event-img" alt="No image">
                        </#if>
                        <div class="card-body">
                            <h5 class="card-title">${event.title()}</h5>
                            <p class="card-text">
                                ${event.address()}, ${event.city()}<br>
                                ${event.timeStart()} - ${event.timeEnd()}<br>
                                <#if event.price()??>Цена: ${event.price()} руб.</#if>
                            </p>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </#if>
</#macro>

<#macro scripts>
</#macro>

<@base.page
title="Мои мероприятия"
head=head
header=header
content=content
scripts=scripts
/>
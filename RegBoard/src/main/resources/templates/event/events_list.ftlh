<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>

<#macro head>
    <style>
        .event-card {
            transition: transform 0.3s;
            height: 100%;
        }
        .event-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }
        .event-img {
            height: 200px;
            object-fit: cover;
        }
        .category-filter {
            margin-bottom: 20px;
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
    <div class="category-filter mb-4">
        <div class="dropdown">
            <button class="btn btn-outline-primary dropdown-toggle" type="button" id="categoryDropdown"
                    data-bs-toggle="dropdown" aria-expanded="false">
                <#if selectedCategory??>
                    <#list categories as category>
                        <#if selectedCategory == category.id()>${category.name()}</#if>
                    </#list>
                <#else>
                    Все категории
                </#if>
            </button>
            <ul class="dropdown-menu" aria-labelledby="categoryDropdown">
                <li><a class="dropdown-item ${(!selectedCategory??)?then('active','')}" href="/event">Все категории</a></li>
                <#list categories as category>
                    <li>
                        <a class="dropdown-item ${(selectedCategory?? && selectedCategory == category.id())?then('active','')}"
                           href="/event?categoryId=${category.id()}">
                            ${category.name()}
                        </a>
                    </li>
                </#list>
            </ul>
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list events as event>
            <div class="col">
                <div class="card event-card">
                    <#if event.imgId()??>
                        <img src="/api/image/${event.imgId()}" class="card-img-top event-img" alt="${event.title()}">
                    <#else>
                        <img src="https://via.placeholder.com/300x200" class="card-img-top event-img" alt="Placeholder">
                    </#if>
                    <div class="card-body">
                        <h5 class="card-title">${event.title()}</h5>
                        <p class="card-text text-muted">
                            <small>${event.timeStart()} - ${event.timeEnd()}</small>
                        </p>
                        <p class="card-text">${event.address()}, ${event.city()}</p>
                        <#if event.price()??>Цена: ${event.price()} руб.</#if>
                        <a href="/event/${event.id()}" class="btn btn-primary">Зарегистрироваться</a>
                    </div>
                </div>
            </div>
        </#list>
    </div>

    <#if totalPages gt 1>
        <nav class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item ${(currentPage == 1)?then('disabled','')}">
                    <a class="page-link" href="?categoryId=${selectedCategory!}&page=${currentPage - 1}">Назад</a>
                </li>
                <#list 1..totalPages as pageNum>
                    <li class="page-item ${(pageNum == currentPage)?then('active','')}">
                        <a class="page-link" href="?categoryId=${selectedCategory!}&page=${pageNum}">${pageNum}</a>
                    </li>
                </#list>
                <li class="page-item ${(currentPage == totalPages)?then('disabled','')}">
                    <a class="page-link" href="?categoryId=${selectedCategory!}&page=${currentPage + 1}">Вперед</a>
                </li>
            </ul>
        </nav>
    </#if>
</#macro>

<#macro scripts>
</#macro>

<@base.page
title="Все мероприятия"
head=head
header=header
content=content
scripts=scripts/>
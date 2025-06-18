<#import "/base.ftlh" as base>
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
    <div class="row justify-content-center">
        <div class="col-md-5 col-lg-4">
            <h1 class="mb-4 text-center">Вход</h1>
            <form action="/account/signIn" method="post" class="p-4 border rounded-3 bg-white shadow-sm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" id="email" name="email" class="form-control <#if errors?? && errors["email"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["email"]??>
                        <div class="invalid-feedback">${errors["email"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Пароль:</label>
                    <input type="password" id="password" name="password" class="form-control <#if errors?? && errors["password"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["password"]??>
                        <div class="invalid-feedback">${errors["password"]}</div>
                    </#if>
                </div>
                <button type="submit" class="btn w-100" style="background-color:#70ABE9; color:#fff; font-weight:600;">Войти</button>
                <div class="mt-3 text-center">
                    <a href="/account/signUp" class="link-secondary">Нет аккаунта? Зарегистрируйтесь</a>
                </div>
            </form>
        </div>
    </div>
</#macro>

<#macro scripts>
</#macro>

<@base.page
title="Вход"
head=head
header=header
content=content
scripts=scripts
/>
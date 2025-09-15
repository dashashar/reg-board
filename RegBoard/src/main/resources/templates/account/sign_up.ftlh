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
        <div class="col-md-6 col-lg-5">
            <h1 class="mb-4 text-center">Регистрация</h1>
            <form action="/account/signUp" method="post" class="p-4 border rounded-3 bg-white shadow-sm">
                <div class="mb-3">
                    <label for="firstName" class="form-label">Имя:</label>
                    <input type="text" id="firstName" name="firstName" value="${(account.firstName())!''}" class="form-control <#if errors?? && errors["firstName"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["firstName"]??>
                        <div class="invalid-feedback">${errors["firstName"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="lastName" class="form-label">Фамилия:</label>
                    <input type="text" id="lastName" name="lastName" value="${(account.lastName())!''}" class="form-control <#if errors?? && errors["lastName"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["lastName"]??>
                        <div class="invalid-feedback">${errors["lastName"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" id="email" name="email" value="${(account.email())!''}" class="form-control <#if errors?? && errors["email"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["email"]??>
                        <div class="invalid-feedback">${errors["email"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Пароль:</label>
                    <input type="password" id="password" name="password" value="${(account.password())!''}" class="form-control <#if errors?? && errors["password"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["password"]??>
                        <div class="invalid-feedback">${errors["password"]}</div>
                    </#if>
                </div>
                <button type="submit" class="btn w-100" style="background-color:#70ABE9; color:#fff; font-weight:600;">Зарегистрироваться</button>
                <div class="mt-3 text-center">
                    <a href="/account/signIn" class="link-secondary">Уже есть аккаунт? Войти</a>
                </div>
            </form>
        </div>
    </div>
</#macro>

<#macro scripts>
</#macro>

<@base.page
title="Регистрация"
head=head
header=header
content=content
scripts=scripts
/>
<#import "/base.ftlh" as base>
<#import "/header.ftlh" as h>

<#macro head>
    <style>
        .empty-answer {
            color: #999;
            font-style: italic;
        }
        .table-container {
            overflow-x: auto;
            width: 100%;
            margin-bottom: 20px;
            border: 1px solid #dee2e6;
            border-radius: 4px;
        }
        .table-responsive table {
            min-width: 100%;
            width: auto;
            margin-bottom: 0;
        }
        .table-responsive th, .table-responsive td {
            white-space: nowrap;
        }
        .table-responsive thead th {
            position: sticky;
            top: 0;
            background: #343a40;
            z-index: 10;
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
    <h1>Регистрации на "${table.eventTitle()}"</h1>

    <div class="table-container">
        <div class="table-responsive">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                <tr>
                    <#list table.headers() as header>
                        <th>${header}</th>
                    </#list>
                </tr>
                </thead>
                <tbody>
                <#list table.rows() as row>
                    <tr>
                        <#list row as cell>
                            <td>
                                <#if cell?has_content>
                                    ${cell}
                                <#else>
                                    <span class="empty-answer">нет ответа</span>
                                </#if>
                            </td>
                        </#list>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</#macro>

<#macro scripts>
    <script>
        $(document).ready(function() {
            $('table').DataTable({
                language: { url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/ru.json' },
                pageLength: 50,
                scrollX: true,
                fixedHeader: true
            });
        });
    </script>
</#macro>

<@base.page
title="Регистрации | ${table.eventTitle()}"
head=head
header=header
content=content
scripts=scripts
/>
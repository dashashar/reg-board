<#macro ticket_form action_url title_text button_text>
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-5">
            <h1 class="mb-4 text-center">${title_text}</h1>
            <form action="${action_url}" method="post" id="form" class="p-4 border rounded-3 bg-white shadow-sm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="mb-3">
                    <label for="name" class="form-label">Название билета</label>
                    <input type="text" id="name" name="name" value="${(ticket.name())!''}" class="form-control <#if errors?? && errors["name"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["name"]??>
                        <div class="invalid-feedback">${errors["name"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Цена билета</label>
                    <input type="number" id="price" name="price" min="0"
                           value="<#if ticket?? && ticket.price()??>${ticket.price()?string.computer}<#else></#if>"
                           class="form-control <#if errors?? && errors["price"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["price"]??>
                        <div class="invalid-feedback">${errors["price"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="totalTickets" class="form-label">Количество билетов</label>
                    <input type="number" id="totalTickets" name="totalTickets" min="0" required
                           value="<#if ticket?? && ticket.totalTickets()??>${ticket.totalTickets()?string.computer}<#else></#if>"
                           class="form-control <#if errors?? && errors["totalTickets"]??>is-invalid</#if>">
                    <#if errors?? && errors["totalTickets"]??>
                        <div class="invalid-feedback">${errors["totalTickets"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="timeStart" class="form-label">Время начала продаж по Московскому времени</label>
                    <input type="datetime-local" id="timeStart" name="salesStart" value="${(ticket.salesStart())!''}" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="timeEnd" class="form-label">Время окончания продаж по Московскому времени</label>
                    <input type="datetime-local" id="timeEnd" name="salesEnd" value="${(ticket.salesEnd())!''}" class="form-control" required>
                </div>
                <div id="dateError" class="text-danger mb-3" style="display: none;">
                    Дата окончания продаж должна быть позже даты начала
                </div>
                <button type="submit" class="btn btn-orange w-100">${button_text}</button>
            </form>
        </div>
    </div>
</#macro>
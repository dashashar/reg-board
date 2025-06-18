<#macro event_form action_url title_text button_text>
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-7">
            <h1 class="mb-4 text-center">${title_text}</h1>
            <form action="${action_url}" method="post" id="form" class="p-4 border rounded-3 bg-white shadow-sm" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="mb-3">
                    <label for="title" class="form-label">Название мероприятия</label>
                    <input type="text" id="title" name="title" value="${(event.title())!''}" class="form-control <#if errors?? && errors["title"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["title"]??>
                        <div class="invalid-feedback">${errors["title"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Описание</label>
                    <textarea id="description" name="description" rows="3" class="form-control <#if errors?? && errors["description"]??>is-invalid</#if>" required>${(event.description())!''}</textarea>
                    <#if errors?? && errors["description"]??>
                        <div class="invalid-feedback">${errors["description"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="timeStart" class="form-label">Дата и время начала</label>
                    <input type="datetime-local" id="timeStart" name="timeStart" value="${(event.timeStart())!''}" class="form-control <#if errors?? && errors["timeStart"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["timeStart"]??>
                        <div class="invalid-feedback">${errors["timeStart"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="timeEnd" class="form-label">Дата и время окончания</label>
                    <input type="datetime-local" id="timeEnd" name="timeEnd" value="${(event.timeEnd())!''}" class="form-control <#if errors?? && errors["timeEnd"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["timeEnd"]??>
                        <div class="invalid-feedback">${errors["timeEnd"]}</div>
                    </#if>
                </div>
                <div id="dateError" class="text-danger mb-3" style="display: none;">
                    Дата окончания должна быть позже даты начала!
                </div>
                <div class="mb-3">
                    <label for="cityInput" class="form-label">Город</label>
                    <input type="text" id="cityInput" name="city" value="${(event.city())!''}" class="form-control <#if errors?? && errors["city"]??>is-invalid</#if>" required>
                    <div id="suggestions"></div>
                    <#if errors?? && errors["city"]??>
                        <div class="invalid-feedback">${errors["city"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">Адрес</label>
                    <input type="text" id="address" name="address" value="${(event.address())!''}" class="form-control <#if errors?? && errors["address"]??>is-invalid</#if>" required>
                    <#if errors?? && errors["address"]??>
                        <div class="invalid-feedback">${errors["address"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="categoryId" class="form-label">Категория</label>
                    <select id="categoryId" name="categoryId" class="form-select <#if errors?? && errors["categoryId"]??>is-invalid</#if>">
                        <#list categories as category>
                            <option value="${category.id()}"
                                    <#if event?? && event.categoryId()?? && event.categoryId() == category.id()>selected</#if>>
                                ${category.name()}
                            </option>
                        </#list>
                    </select>
                    <#if errors?? && errors["categoryId"]??>
                        <div class="invalid-feedback">${errors["categoryId"]}</div>
                    </#if>
                </div>
                <div class="mb-3">
                    <label for="imageUpload" class="form-label">Изображение</label>
                    <input type="file" id="imageUpload" accept="image/jpeg, image/jpg"
                           class="form-control"
                           style="display: ${(event?? && event.imgId()??)?then('none','block')}">
                    <div id="currentImageContainer"
                         style="display: ${(event?? && event.imgId()??)?then('block','none')}">
                        <#if event?? && event.imgId()??>
                            <img src="/api/image/${event.imgId()}" alt="Current Image" style="max-width: 200px;" class="mb-2">
                            <button type="button" id="changeImageBtn" class="btn btn-outline-secondary btn-sm">Изменить изображение</button>
                        </#if>
                    </div>
                    <input type="hidden" id="imgId" name="imgId" value="${(event.imgId())!''}">
                    <div id="imagePreview" style="margin-top: 10px;"></div>
                </div>
                <button type="submit" class="btn btn-orange w-100">${button_text}</button>
            </form>
        </div>
    </div>
</#macro>
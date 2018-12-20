<#macro requestEdit path>

    <#if path=="/main">
        <a class="btn btn-secondary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
           aria-controls="collapseExample">Добавить заявку
        </a>
    </#if>

    <div class="collapse <#if request?? || fileError??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                           value="<#if request??>${request.text}</#if>" name="text" placeholder="Описание изделия"/>
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control ${(ptypeError??)?string('is-invalid','')}"
                           value="<#if request??>${request.ptype}</#if>" name="ptype" placeholder="Тип изделия"/>
                    <#if ptypeError??>
                        <div class="invalid-feedback">
                            ${ptypeError}
                        </div>
                    </#if>
                </div>

                <div class="custom-file mb-4">
                    <input type="file" class="form-control-file ${(fileError??)?string('is-invalid','')}"
                           name="file" title="Выбрать файл" />
                    <#if fileError??>
                        <div class="invalid-feedback">
                            ${fileError}
                        </div>
                    </#if>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <#if path!="/main">
                    <input type="hidden" name="id" value="<#if request??>${request.id}</#if>"/>
                </#if>
                <div class="form-group">
                    <button type="submit" class="btn btn-secondary"><#if path!="/main">Сохранить<#else>Отправить</#if></button>
                </div>
            </form>
        </div>
    </div>
</#macro>
<#macro base links>
    <nav class="navbar navbar-expand-lg" style="background-color: #70ABE9; margin:0; padding:0; border-radius:0;">
        <div class="container-fluid" style="padding-left:0; padding-right:0; margin:0;">
            <a class="navbar-brand text-uppercase fw-bold"
               href="#"
               style="color:#fff; font-size:2rem; letter-spacing:2px; margin-left:48px;">
                RerBoard
            </a>
            <div class="ms-auto" style="margin-right:48px;">
                <ul class="navbar-nav flex-row gap-3">
                    <#list links as link>
                        <li class="nav-item">
                            <a class="nav-link fw-bold"
                               href="${link.url}"
                               style="color:#fff; font-size:1.15rem; padding-left:18px; padding-right:18px;">
                                ${link.text}
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </nav>
</#macro>
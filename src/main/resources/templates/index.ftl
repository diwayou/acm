<#list news as new>
<div>
    <h3><a href="${new.url}" target="_blank">${new.title}</a></h3>
    <h5>${new.meta}</h5>
    <p>${new.body}</p>
</div>
</#list>
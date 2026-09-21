document.querySelector('.nav-toggle')?.addEventListener('click',()=>document.querySelector('.topbar nav')?.classList.toggle('open'));
document.querySelectorAll('[data-open]').forEach(b=>b.addEventListener('click',()=>document.getElementById(b.dataset.open)?.showModal()));
document.querySelectorAll('[data-close]').forEach(b=>b.addEventListener('click',()=>b.closest('dialog')?.close()));
document.querySelectorAll('.confirm-delete').forEach(f=>f.addEventListener('submit',e=>{if(!confirm('Delete this appointment permanently?'))e.preventDefault();}));
document.querySelectorAll('[data-edit]').forEach(b=>b.addEventListener('click',()=>{const d=document.getElementById('edit-dialog'),f=d.querySelector('form');['id','date','time','status','reason'].forEach(k=>f.elements[k].value=b.dataset[k]||'');d.showModal();}));
document.querySelectorAll('input[type=date]').forEach(i=>{const d=new Date();d.setDate(d.getDate()+1);i.min=d.toISOString().split('T')[0];});

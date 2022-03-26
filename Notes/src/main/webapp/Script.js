var get=document.getElementById('in');
var ps=document.getElementById('psave');
var edi=document.getElementById('editer');
var txtAreaForEdit=document.getElementById('text_area');
var elementInEditMode;
var x=1;
var pbox;
var text;
var itra=1;
document.getElementById('save_btn').addEventListener('click',close);
document.getElementById('cancel_btn').addEventListener('click',cancel);
document.getElementById('delete_btn').addEventListener('click',delet);
let data=""
document.addEventListener("DOMContentLoaded", async()=>{
  var uid =document.cookie.split("=")[1];
  var result=await fetch("http://localhost:8081/Notes/FetchServlet?uid="+uid,{method:"get"});
  data = await result.json()
    console.log(data)
    //itra=data.length;
    data.forEach(printing)
});
function printing(data){
   console.log(data["Title"]);
  add();
  var qu="p[id='"+itra+"']";
  var p=document.createElement("h1");
  p.innerText=data["Title"];
  p.style.textAlign="center";
  p.style.border="none";
  document.querySelector(qu).appendChild(p);
  var decs=document.createElement("p")
  decs.innerText=data["Note"]
  decs.style.border="none";
  document.querySelector(qu).appendChild(decs)
 // document.querySelector(qu).innerHTML+="<br>"+data["Note"];
  itra++;
 
  //console.log(data["Note"]);
}
function add(){
   if(true){
    pbox=document.createElement('p');
    console.log(pbox);
    pbox.className ="creater-us";
    pbox.setAttribute("id",x);
    pbox.setAttribute("onclick","edit(event)");
    document.getElementById("title").value=""
    ps.append(pbox);
    console.log(ps);
    x++;
   }
}

function edit(event){
    console.log("in for edit");
    elementInEditMode=event.target.id;
    edi.style.display="flex";
    document.getElementById('title').value=document.querySelector(`#${CSS.escape(1)}`).childNodes[0].innerText;
    var not=event.target.textContent;
    not=not.substring(document.querySelector(`#${CSS.escape(elementInEditMode)}`).textContent.length);
    txtAreaForEdit.value=document.querySelector(`#${CSS.escape(elementInEditMode)}`).childNodes[1].innerText;
    
}

async function close(){
    console.log("in for close");
    var val=txtAreaForEdit.value;
    console.log(val);
    edi.style.display='none';
    var til=document.getElementById('title').value;
    document.getElementById(elementInEditMode).innerHTML='<h1 style="text-align:center;border:none">'+til+'</h1>';
    document.getElementById(elementInEditMode).innerHTML+=val;
    var result=await fetch("http://localhost:8081/Notes/NotesServlet?notes="+val+"&title="+til,{method:"post"});
    var data = await result.json()
    console.log(data)
}


function cancel(){
    edi.style.display='none';
    document.getElementById('title').value = "";
}

async function delet(){
    document.getElementById(elementInEditMode).remove();
    edi.style.display='none';
    var title = document.getElementById('title').value;
    var uid =document.cookie.split("=")[1];
    var result=await fetch("http://localhost:8081/Notes/DServlet?uid="+uid+"&title="+title,{method:"post"});
    var data = await result.json()
    console.log(data)
    document.getElementById('title').value = "";
    
}


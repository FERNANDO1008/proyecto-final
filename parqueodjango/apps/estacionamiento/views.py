from django.shortcuts import render,redirect
from django.contrib.auth.decorators import login_required
from .forms import FormularioEstacionamiento,FormularioCrearEstacion
from apps.persona.forms import FormularioPersona
from apps.ticket.forms import FormularioTicket
from apps.modelo.models import Estacionamiento,Sitio,Persona,Ticket
from apps.sitio.forms import FormularioCrearSitio
from django.http import JsonResponse
from django.contrib import messages
# def inicio(request): 
#     return render(request,'index.html')

@login_required
def listaEstacionamineto(request): #canaliza peticiones
    listaEsta= Estacionamiento.objects.all() # objects o sqlAlc metodos de orm para agrgar todo los datos en la lista
    listaClie= Persona.objects.all() # objects o sqlAlc metodos de orm para agrgar todo los datos en la lista
    formulario=FormularioEstacionamiento()#se crea una peticion de tipo post
    formularioCrear=FormularioCrearEstacion()#se crea una peticion de tipo post
    formularioCrearSitio=FormularioCrearSitio()#se crea una peticion de tipo post
    formularioTick=FormularioTicket();
    context={
        'lista':listaEsta,
        'fE': formulario,
        'fT':formularioTick,
        'fC':formularioCrear,
        'fS':formularioCrearSitio,
        'fP':listaClie,
        'activeE':1,
    }
    return render(request,'estacionamiento/listEstacionamiento.html',context)#cargar la vista sirve el rnder

# def crear(request):
   

# def modificar(request):
    

def cargarDatos(request):
    id=request.GET['idEstac']
    estac= Estacionamiento.objects.get(estacion_id= id)
    listsit= Sitio.objects.filter(Estacionamiento_id= estac.estacion_id).values('numero','estado','sitio_id')
    listsitio = list(listsit)
    #print (listsitio)
    data = {
        'localizacion': estac.localizacion,
        'calle_pri': estac.calle_primaria,
        'calle_sec': estac.calle_secundaria,
        'telefono': estac.telefono,
        'celular': estac.celular,
        'lista':listsitio
    }
    return JsonResponse(data)
def cargarDetalle(request):
    id=request.GET['id_sitio']
    ticke= Ticket.objects.get(ticket_id= id)
    persona= Persona.objects.get(persona_id= ticke.Persona_id)
    # print (persona)
    data = {
        'cedula': persona.cedula,
        'nombres': persona.nombres,
        'apellidos': persona.apellidos,
        'fechainic':ticke.fechaInicio,
        'fechafin':ticke.fechaFin,
        'horainic':ticke.horaInicio,
        'horafin':ticke.horaFin,
        'costoHora':ticke.costoHora,
        'costoTotal':ticke.costoTotal,
    }
    return JsonResponse(data)

def crear(request):
    # usuario=request.user #si la petiion e sprocesada por el framework agrega el user para verificar si el usuario esta logueado
    formulario=FormularioCrearEstacion(request.POST)
    if request.method=='POST':
        if formulario.is_valid():
            datos=formulario.cleaned_data  #obteniendo todo los datos del formulario
            estacion = Estacionamiento() #crea objeto en python
            estacion.nombre=datos.get('nombre')
            estacion.localizacion=datos.get('localizacion')
            # estacion.latitud=datos.get('latitud')
            # estacion.longitud=datos.get('longitud')
            estacion.calle_primaria=datos.get('calle_primaria')
            estacion.calle_secundaria=datos.get('calle_secundaria')
            estacion.telefono=datos.get('telefono')
            estacion.celular=datos.get('celular')
            estacion.save()
            messages.add_message(request,messages.SUCCESS,'Estacionamiento guardado Correctamente')
        else:
           messages.add_message(request,messages.ERROR,'No se guardo el registro')
    return redirect(listaEstacionamineto)
    # context={
    #     'f': formulario,
    #     'fc':formularioCuenta,
    # }
    # return render(request,'estacion/Agregarestacion.html',context)
def crearSitio(request):
    formulario=FormularioCrearSitio(request.POST)
    if request.method=='POST':
        if formulario.is_valid():
            datos=formulario.cleaned_data  #obteniendo todo los datos del formulario
            id_esta= request.POST.get('id_Estacionamiento')
            estac_id= Estacionamiento.objects.get(estacion_id=id_esta)
            sitio = Sitio() #crea objeto en python
            sitio.numero=datos.get('numero')
            sitio.estado=1
            sitio.Estacionamiento=estac_id
            sitio.save()
            messages.add_message(request,messages.SUCCESS,'Sitio guardado Correctamente')
        else:
           messages.add_message(request,messages.ERROR,'No se guardo el registro')
    return redirect(listaEstacionamineto)
def ticket(request):
    id_pers= request.POST.get('selPersona')
    id_sitio= request.POST.get('id_sitioTick')
    persona= Persona.objects.get(persona_id=id_pers)
    sitio=Sitio.objects.get(sitio_id=id_sitio)
    print(persona.persona_id)
    print(sitio.sitio_id)
    formulario=FormularioTicket(request.POST)
    if request.method=='POST':
        # if formulario.is_valid():
        # datos=formulario.cleaned_data  #obteniendo todo los datos del formulario
        sitio.estado=0
        sitio.save()
        tikt = Ticket() #crea objeto en python
        tikt.fechaInicio=request.POST.get('fechaInicio')
        tikt.fechaFin=request.POST.get('fechaFin')
        tikt.costoHora=request.POST.get('costoHora')
        tikt.costoTotal=request.POST.get('costoTotal')
        tikt.horaInicio=request.POST.get('horaInicio')
        tikt.horaFin=request.POST.get('horaFin')
        tikt.Persona_id=persona.persona_id
        tikt.Sitio_id=sitio.sitio_id
        print(tikt)
        tikt.save()
        messages.add_message(request,messages.SUCCESS,'Reserva exitosa')
        # else:
        # messages.add_message(request,messages.ERROR,'No se guardo el registro')
    return redirect(listaEstacionamineto)
def activarSitio(request):
    id_sitio= request.POST.get('id_sitioTick1')
    sitio=Sitio.objects.get(sitio_id=id_sitio)
    if request.method=='POST':
        sitio.estado=1
        sitio.save()
        messages.add_message(request,messages.SUCCESS,'Activacion exitosa')
    return redirect(listaEstacionamineto)

from django.contrib.auth.decorators import login_required
from django.shortcuts import render,redirect
from .forms import FormularioPersona,FormularioModificaPersona
from apps.cuenta.forms import FormularioCuenta
from apps.ticket.forms import FormularioTicket
from apps.vehiculo.forms import FormularioVehiculo
# from apps.estacionamiento.forms import FormularioVehiculo
from apps.modelo.models import Persona,Cuenta,Vehiculo,Ticket,Sitio,Estacionamiento
from django.http import JsonResponse
from django.contrib import messages
def inicio(request): 
    return render(request,'index.html')

@login_required
def listaPersona(request): #canaliza peticiones
    listaPer= Persona.objects.all().order_by('apellidos') # objects o sqlAlc metodos de orm para agrgar todo los datos en la lista
    # listaPer= Persona.objects.all().values('nombres','apellidos') # objects o sqlAlc metodos de orm para agrgar todo los datos en la lista
    # users_list = list(listaPer)
    # return JsonResponse(users_list, safe=False)
    formulario=FormularioPersona()#se crea una peticion de tipo post
    formularioModifica=FormularioModificaPersona()#se crea una peticion de tipo post
    formularioCuenta=FormularioCuenta()#para el formulario de cuenta
    context={
        'lista':listaPer,
        'f': formulario,
        'fM': FormularioModificaPersona,
        'fc':formularioCuenta,
        'activeC':1,
    }
    return render(request,'persona/listaPersona.html',context)#cargar la vista sirve el rnder
@login_required
def crear(request):
    # usuario=request.user #si la petiion e sprocesada por el framework agrega el user para verificar si el usuario esta logueado
    formulario=FormularioPersona(request.POST)#se crea una peticion de tipo post
    formularioCuenta=FormularioCuenta(request.POST)#para el formulario de cuenta
    if request.method=='POST':
        if formulario.is_valid() and formularioCuenta.is_valid():
            datos=formulario.cleaned_data  #obteniendo todo los datos del formulario
            persona = Persona() #crea objeto en python
            persona.cedula=datos.get('cedula')
            persona.nombres=datos.get('nombres')
            persona.apellidos=datos.get('apellidos')
            persona.direccion=datos.get('direccion')
            persona.telefono=datos.get('telefono')
            persona.celular=datos.get('celular')
            persona.save()
            datosCuenta=formularioCuenta.cleaned_data#obteniendo todo los datos del formulario Cuenta
            cuenta=Cuenta() #crear u objeto de la clase cuenta
            cuenta.correo=datosCuenta.get('correo')
            cuenta.clave=datosCuenta.get('clave')
            cuenta.estado=True
            cuenta.Persona=persona
            cuenta.save()
            messages.add_message(request,messages.SUCCESS,'Persona guardado Correctamente')
        else:
           messages.add_message(request,messages.ERROR,'No se guardo el registro')

    return redirect(listaPersona)
    # context={
    #     'f': formulario,
    #     'fc':formularioCuenta,
    # }
    # return render(request,'persona/AgregarPersona.html',context)
def modificar(request):
    # usuario=request.user #si la petiion e sprocesada por el framework agrega el user para verificar si el usuario esta logueado
    formulario=FormularioModificaPersona(request.POST)
    cedulaP=request.POST.get('cedulaM')
    persona=Persona.objects.get(cedula=cedulaP)
    print (persona.cedula)

    if request.method=='POST':
        if formulario.is_valid():
            datos=formulario.cleaned_data  #obteniendo todo los datos del formulario
            print ("llega")
            persona.nombres=datos.get('nombres')
            persona.apellidos=datos.get('apellidos')
            persona.direccion=datos.get('direccion')
            persona.telefono=datos.get('telefono')
            persona.celular=datos.get('celular')
            persona.save()
            messages.add_message(request,messages.SUCCESS,'Actualizacion exitosa')
        else:
           messages.add_message(request,messages.ERROR,'No se actualizo registro')
    
    return redirect(listaPersona)
    # context={
    #     'f': formulario,
    # }
    # return render(request,'persona/ModificarPersona.html',context)


def cargarDatos(request):
    dni=request.GET['cedula']
    print(dni)
    persona= Persona.objects.get(cedula= dni)
    # print (persona)
    data = {
        'cedula': persona.cedula,
        'nombres': persona.nombres,
        'apellidos': persona.apellidos,
        'direccion': persona.direccion,
        'telefono': persona.telefono,
        'celular': persona.celular,
    }
    return JsonResponse(data)
def cargarDatosServicio(request):
    dni=request.GET['cedula']
    print(dni)
    persona= Persona.objects.get(cedula= dni)
    # print (persona)
    data = {
        'cedula': persona.cedula,
        'nombres': persona.nombres,
        'apellidos': persona.apellidos,
        'direccion': persona.direccion,
        'telefono': persona.telefono,
        'celular': persona.celular,
    }
    return JsonResponse(data)
def vehiculo(request,cedulaP): 
    listaEsta= Estacionamiento.objects.all()
    formulario=FormularioVehiculo(request.POST)
    formularioEsta=(request.POST)
    formularioTicket=FormularioTicket(request.POST)
    persona=Persona.objects.get(cedula=cedulaP)
    vehiculoLista= Vehiculo.objects.filter(Persona_id =persona.persona_id)   
    if request.method=='POST':
        if formulario.is_valid():
            datos=formulario.cleaned_data#obteniendo todo los datos del formulario Cuenta
            vehiculo=Vehiculo() #crear u objeto de la clase cuenta
            vehiculo.nro_placa=datos.get('nro_placa')
            vehiculo.marca=datos.get('marca')
            vehiculo.color=datos.get('color')
            vehiculo.Persona_id=persona.persona_id
            vehiculo.save()
        if formularioTicket.is_valid():
            datos=formularioTicket.cleaned_data#obteniendo todo los datos del formulario Cuenta
            ticket=Ticket() #crear u objeto de la clase cuenta
            tikt.fechaInicio=datos.get('fechaInicio')
            tikt.fechaFin=datos.get('fechaFin')
            tikt.costoHora=datos.get('costoHora')
            tikt.costoTotal=datos.get('costoTotal')
            tikt.horaInicio=datos.get('horaInicio')
            tikt.horaFin=datos.get('horaFin')
            tikt.Persona_id=persona.persona_id
            tikt.Sitio_id=sitio.sitio_id
            vehiculo.save()
    context={
        'dni':cedulaP,
        'personas':persona,
        'fc':formulario,
        'fT':formularioTicket,
        'lista':vehiculoLista,
        'listEs':listaEsta,
    }
    return render(request,'vehiculo/vehiculo.html',context)

def ticket(request):
    id_pers= request.POST.get('cedula_persona')
    id_sitio= request.POST.get('sitioEsta')
    persona= Persona.objects.get(cedula=id_pers)
    sitio=Sitio.objects.get(sitio_id=id_sitio)
    print(persona.persona_id)
    print(sitio.sitio_id)
    formulario=FormularioTicket(request.POST)
    if request.method=='POST':
        # if formulario.is_valid():
        print("llega")
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
    return redirect(listaPersona)
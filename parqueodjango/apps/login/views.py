from django.contrib.auth import authenticate, login, logout
from django.http import HttpResponseRedirect
from django.urls import reverse
from django.shortcuts import render
from apps.login.forms import FormularioLogin
from django.contrib import messages

# Create your views here.
def ingresar(request):
    if request.method=='POST':
        formulario=FormularioLogin(request.POST)
        if formulario.is_valid():
            usuario=request.POST['username']
            clave=request.POST['password']
            user=authenticate(username=usuario,password=clave)
            if user is not None:#verfica que no sea nulo
                if user.is_active: #verifica si el usuario est activado
                    login(request,user)
                    messages.add_message(request,messages.SUCCESS,'usuario correcto')
                    return HttpResponseRedirect(reverse('listaPersona'))
                else:
                    messages.add_message(request,messages.ERROR,'usuario desactivado')
                    # print('usuario desactivado')            
            else:
                messages.add_message(request,messages.ERROR,'Usuario y/o contraseña invalido')
                # print ('Usuario y/o contraseña invalido')
    else:
        formulario=FormularioLogin()

    context={
        'fl':formulario,
    }
    return  render(request,'principal/login.html',context)


def cerrar(request):
    logout(request)
    return HttpResponseRedirect(reverse('home_page'))
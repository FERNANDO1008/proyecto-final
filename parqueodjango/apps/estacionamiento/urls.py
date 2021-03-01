
from django.urls import path

from . import views
urlpatterns = [
    path('lista/',views.listaEstacionamineto,name='listaEstacionamineto'),# se deja la direcicon basia para saber que es el index
    path('crear',views.crear, name='crearEstacioamiento'),
    path('crearSitio',views.crearSitio, name='crearSitio'),
    # path('modificar',views.modificar,name='modificarPersona'),
    path('cargarDatos',views.cargarDatos),
    path('cargarDetalle',views.cargarDetalle),
    path('ticket_reg',views.ticket,name='ticket'),
    path('activarSitio',views.activarSitio,name='activar_sitio'),
]
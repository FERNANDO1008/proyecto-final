
from django.urls import path

from . import views
urlpatterns = [
    path('listaPersona/',views.listaPersona,name='listaPersona'),# se deja la direcicon basia para saber que es el index
    path('crear',views.crear, name='crearPersona'),
    path('modificar',views.modificar,name='modificarPersona'),
    path('cargarPersona',views.cargarDatos),
    path('cargarServicio',views.cargarDatosServicio),
    path(r'vehiculos/(?P<cedulaP>d+)/$',views.vehiculo,name='vehiculo'),
    path('ticket_regV',views.ticket,name='ticketVeh'),
]
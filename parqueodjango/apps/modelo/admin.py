from django.contrib import admin

# Register your models here.
from .models import Persona
#crea un administrador utilizando herencia ModelAdmin
class AdminPersona(admin.ModelAdmin):
    list_display=["cedula","apellidos","nombres","direccion","telefono"]
    list_editable=["apellidos","nombres","telefono"]
    list_filter=["apellidos","nombres"]
    search_fields=["cedula","apellidos"]

    class Meta:
        model=Persona
admin.site.register(Persona, AdminPersona)


from .models import Cuenta
#crea un administrador utilizando herencia ModelAdmin
class AdminCuenta(admin.ModelAdmin):
    list_display=["correo","clave"]
    # list_editable=["correo"]
    list_filter=["correo"]
    search_fields=["correo"]
    
    class Meta:
        model=Cuenta

admin.site.register(Cuenta, AdminCuenta)

from .models import Vehiculo
#crea un administrador utilizando herencia ModelAdmin
class AdminVehiculo(admin.ModelAdmin):
    list_display=["nro_placa","marca","color"]
    # list_editable=["correo"]
    list_filter=["nro_placa","marca"]
    search_fields=["nro_placa","marca"]
    class Meta:
        model=Vehiculo

admin.site.register(Vehiculo, AdminVehiculo)

from .models import Estacionamiento
#crea un administrador utilizando herencia ModelAdmin
class AdminEstacionamiento(admin.ModelAdmin):
    list_display=["nombre","localizacion","telefono","celular"]
    list_editable=["localizacion","telefono","celular"]
    list_filter=["nombre","localizacion"]
    search_fields=["localizacion","localizacion"]
    class Meta:
        model=Estacionamiento

admin.site.register(Estacionamiento, AdminEstacionamiento)

from .models import Sitio
#crea un administrador utilizando herencia ModelAdmin
class AdminSitio(admin.ModelAdmin):
    list_display=["numero","estado"]
    list_editable=["estado"]
    list_filter=["numero","estado"]
    search_fields=["numero","estado"]
    class Meta:
        model=Sitio

admin.site.register(Sitio, AdminSitio)


from .models import Ticket
#crea un administrador utilizando herencia ModelAdmin
class Adminticket(admin.ModelAdmin):
    list_display=["fechaInicio","fechaFin","costoHora","costoTotal","horaInicio","horaFin"]
    list_editable=["fechaFin","costoHora","costoTotal","horaInicio","horaFin"]
    list_filter=["fechaInicio","horaInicio"]
    search_fields=["fechaInicio","horaInicio"]
    class Meta:
        model=Ticket

admin.site.register(Ticket, Adminticket)
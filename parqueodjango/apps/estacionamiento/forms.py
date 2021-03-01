from django import forms
from apps.modelo.models import Estacionamiento

class FormularioEstacionamiento (forms.ModelForm):
    class Meta:
        model=Estacionamiento
        fields=["nombre","localizacion","telefono","celular"] #campos que se representan de forma grafica
        labels={
            "nombre":"Nombre",
            "localizacion":"Localización",
            "telefono":"Telefono",
            "celular":"Celular",
        }
        widgets = {
            'nombre': forms.TextInput(attrs={'class': 'form-control','placeholder':'Nombres...'}),
            'localizacion': forms.TextInput(attrs={'class': 'form-control','placeholder':'Localizacion...'}),
            'telefono': forms.TextInput(attrs={'class': 'form-control','placeholder':'Telefono...'}),
            'celular': forms.TextInput(attrs={'class': 'form-control','placeholder':'Celular...'}),
        }
class FormularioCrearEstacion (forms.ModelForm):
    class Meta:
        model=Estacionamiento
        fields=["nombre","localizacion","latitud","longitud","calle_primaria","calle_secundaria","telefono","celular"] #campos que se representan de forma grafica
        labels={
            "nombre":"Nombre",
            "localizacion":"Localización",
            "latitud":"Latitud",
            "longitud":"Longitud",
            "calle_primaria":"Calle Principal",
            "calle_secundaria":"Calle secundaria",
            "telefono":"Telefono",
            "celular":"Celular",
        }
        widgets = {
            'nombre': forms.TextInput(attrs={'class': 'form-control','placeholder':'Nombres...','id':'id_nombresC','maxlength':'30'}),
            'localizacion': forms.TextInput(attrs={'class': 'form-control','placeholder':'Localizacion...','id':'id_localizacionC','maxlength':'30'}),
            'latitud': forms.TextInput(attrs={'class': 'form-control','placeholder':'Latitud...','id':'id_latitudC','maxlength':'50'}),
            'longitud': forms.TextInput(attrs={'class': 'form-control','placeholder':'Longitud...','id':'id_longitudC','maxlength':'50'}),
            'calle_primaria': forms.TextInput(attrs={'class': 'form-control','placeholder':'Calle principal...','id':'id_calleprinC','maxlength':'50'}),
            'calle_secundaria': forms.TextInput(attrs={'class': 'form-control','placeholder':'Calle secundaria...','id':'id_callesecuC','maxlength':'50'}),
            'telefono': forms.TextInput(attrs={'class': 'form-control','placeholder':'Telefono...','id':'id_telefonoC','maxlength':'10','onkeypress':'return validaNumero(event)'}),
            'celular': forms.TextInput(attrs={'class': 'form-control','placeholder':'Celular...','id':'id_celularC','maxlength':'10','onkeypress':'return validaNumero(event)'}),
        }


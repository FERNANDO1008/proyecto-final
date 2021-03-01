from django import forms
from apps.modelo.models import Persona

class FormularioPersona (forms.ModelForm):
    class Meta:
        model=Persona
        fields=["cedula","nombres","apellidos","direccion","telefono","celular"] #campos que se representan de forma grafica
        labels={
            "cedula":"Cedula",
            "nombres":"Nombres",
            "apellidos":"Apellidos",
            "direccion":"Dirección",
            "telefono":"Telefono",
            "celular":"Celular",
        }
        widgets = {
            'cedula': forms.TextInput(attrs={'class': 'form-control','placeholder':'Cedula...','maxlength':'10','onkeypress':'return validaNumero(event)'}),
            'nombres': forms.TextInput(attrs={'class': 'form-control','placeholder':'Nombres...','maxlength':'30','onkeypress':'return validaletra(event)'}),
            'apellidos': forms.TextInput(attrs={'class': 'form-control','placeholder':'Apellidos...','maxlength':'30','onkeypress':'return validaletra(event)'}),
            'telefono': forms.TextInput(attrs={'class': 'form-control','placeholder':'Telefono...','maxlength':'30','onkeypress':'return validaNumero(event)'}),
            'celular': forms.TextInput(attrs={'class': 'form-control','placeholder':'Celular...','maxlength':'30','onkeypress':'return validaNumero(event)'}),
            'direccion': forms.Textarea(attrs={'class': 'form-control','cols': 10, 'rows': 2,'value':'Sin dirección',}),
        }
class FormularioModificaPersona (forms.ModelForm):
    class Meta:
        model=Persona
        fields=["nombres","apellidos","direccion","telefono","celular"] #campos que se representan de forma grafica
        labels={
            # "cedulaM":"Cedula",
            "nombresM":"Nombres",
            "apellidosM":"Apellidos",
            "direccionM":"Dirección",
            "telefonoM":"Telefono",
            "celularM":"Celular",
        }
        widgets = {
            # 'cedula': forms.TextInput(attrs={'class': 'form-control','placeholder':'Cedula...','id':'id_cedulaM','readonly':True}),
            'nombres': forms.TextInput(attrs={'class': 'form-control','placeholder':'Nombres...','id':'id_nombresM','onkeypress':'return validaNumero(event)'}),
            'apellidos': forms.TextInput(attrs={'class': 'form-control','placeholder':'Apellidos...','id':'id_apellidosM','onkeypress':'return validaNumero(event)'}),
            'telefono': forms.TextInput(attrs={'class': 'form-control','placeholder':'Telefono...','id':'id_telefonoM','onkeypress':'return validaNumero(event)'}),
            'celular': forms.TextInput(attrs={'class': 'form-control','placeholder':'Celular...','id':'id_celularM','onkeypress':'return validaNumero(event)'}),
            'direccion': forms.Textarea(attrs={'class': 'form-control','cols': 10, 'rows': 2,'value':'Sin dirección','id':'id_direccionM'}),
        }
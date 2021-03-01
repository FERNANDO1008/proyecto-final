from django import forms
from apps.modelo.models import Cuenta

class FormularioCuenta (forms.ModelForm):
    class Meta:
        model=Cuenta
        fields=["correo","clave"] #campos que se representan de forma grafica
        labels={
            "correo":"Correo",
            "clave":"Clave",
        }
        widgets = {
            'clave': forms.TextInput(attrs={'class': 'form-control','placeholder':'Clave...','maxlength':'10'}),
            'correo': forms.EmailInput(attrs={'class': 'form-control','placeholder':'Correo...','maxlength':'50'}),
        }
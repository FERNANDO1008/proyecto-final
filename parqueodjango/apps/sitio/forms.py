from django import forms
from apps.modelo.models import Sitio

class FormularioSitio (forms.ModelForm):
    class Meta:
        model=Sitio
        fields=["numero","estado"] #campos que se representan de forma grafica
        labels={
            "numero":"Número",
            "estado":"Estado",
        }
        widgets = {
            'numero': forms.TextInput(attrs={'class': 'form-control','placeholder':'Numero...','maxlength':'10'}),
            'estado': forms.TextInput(attrs={'class': 'form-control','placeholder':'Estado...'}),
        }
class FormularioCrearSitio (forms.ModelForm):
    class Meta:
        model=Sitio
        fields=["numero","estado"] #campos que se representan de forma grafica
        labels={
            "numero":"Número",
            "estado":"Estado",
        }
        widgets = {
            'numero': forms.TextInput(attrs={'class': 'form-control','placeholder':'Numero...','type':'number'}),
            'estado': forms.TextInput(attrs={'class': 'form-control','placeholder':'Estado...','value':'Activo', 'readonly':'true'}),
        }
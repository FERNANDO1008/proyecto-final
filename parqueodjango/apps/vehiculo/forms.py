from django import forms
from apps.modelo.models import Vehiculo

class FormularioVehiculo (forms.ModelForm):
    class Meta:
        model=Vehiculo
        fields=["nro_placa","marca","color"] #campos que se representan de forma grafica
        labels={
            "nro_placa":"Nro Placa",
            "marca":"Marca",
            "color":"Color",
        }
        widgets = {
            'nro_placa': forms.TextInput(attrs={'class': 'form-control','placeholder':'Nro Placa'}),
            'marca': forms.TextInput(attrs={'class': 'form-control','placeholder':'Marca'}),
            'color': forms.TextInput(attrs={'class': 'form-control','placeholder':'Color','onkeypress':'return validaletra(event)'}),
        }
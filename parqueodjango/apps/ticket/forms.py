from django import forms
from apps.modelo.models import Ticket

class FormularioTicket (forms.ModelForm):
    class Meta:
        model=Ticket
        fields=["fechaInicio","fechaFin","costoHora","costoTotal","horaInicio","horaFin"] #campos que se representan de forma grafica
        labels={
            "fechaInicio":"Fecha Ingreso",
            "fechaFin":"Fecha Salida",
            "costoHora":"Costo Hora",
            "costoTotal":"Costo Total",
            "horaInicio":"Hora de Ingreso",
            "horaFin":"Hora de salida",
        }
        widgets = {
            'fechaInicio': forms.DateInput(attrs={'class': 'form-control','type':'date'}),
            'fechaFin': forms.DateInput(attrs={'class': 'form-control','type':'date'}),
            'costoHora': forms.NumberInput(attrs={'class': 'form-control','onkeypress':'return validaNumeroReales(event,this)'}),
            'costoTotal': forms.NumberInput(attrs={'class': 'form-control','onkeypress':'return validaNumeroReales(event,this)'}),
            'horaInicio': forms.TimeInput(attrs={'class': 'form-control','type':'time','list':"listalimitestiempo",'step':'0.001'}),
            'horaFin': forms.TimeInput(attrs={'class': 'form-control','type':'time','list':"listalimitestiempo",'step':'0.001'}),
        }
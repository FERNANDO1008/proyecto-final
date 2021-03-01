from django import forms


#permite crear a nano el formulario
class FormularioLogin(forms.Form):
    username=forms.CharField(widget=forms.TextInput(attrs={'class': 'form-control','placeholder':'Usuario'}))
    password=forms.CharField(widget=forms.PasswordInput(attrs={'class': 'form-control','placeholder':'Clave'}))

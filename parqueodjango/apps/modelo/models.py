from django.db import models

#modelo persona
class Persona(models.Model):
    #Se declara un id del cliente como clave primaria
    persona_id=models.AutoField(primary_key=True)
    cedula=models.CharField(max_length=10,unique=True,null=False)
    nombres=models.CharField(max_length=50,null=False)
    apellidos=models.CharField(max_length=50,null=False)
    direccion=models.TextField(max_length=50,default='sin dirección')
    telefono=models.CharField(max_length=13)
    celular=models.CharField(max_length=13)

    def __str__(self):
        # string=str(self.nombres)+" "+str(self.apellidos)
        return self.nombres
#modelo cuenta de la persona
class Cuenta(models.Model):
    cuenta_id=models.AutoField(primary_key=True)
    correo=models.CharField(max_length=50,unique=True,null=False)
    clave=models.CharField(max_length=50,null=False)
    estado=models.BooleanField(default=True)
    Persona=models.ForeignKey(
        'Persona',
        on_delete=models.CASCADE,
    )

    def __str__(self):
        return self.correo
#modelo vehiculo de la persona
class Vehiculo(models.Model):
    vehiculo_id=models.AutoField(primary_key=True)
    nro_placa=models.CharField(max_length=10,unique=True,null=False)
    marca=models.CharField(max_length=50,null=False)
    color=models.CharField(max_length=50,null=False)
    Persona=models.ForeignKey(
        'Persona',
        on_delete=models.CASCADE,
    )

    def __str__(self):
        return self.nro_placa

class Estacionamiento(models.Model):
    estacion_id=models.AutoField(primary_key=True)
    nombre=models.CharField(max_length=50,null=False)
    localizacion=models.CharField(max_length=50,null=False)
    longitud=models.DecimalField(max_digits=9, decimal_places=6, null=True, blank=True)
    latitud=models.DecimalField(max_digits=9, decimal_places=6, null=True, blank=True)
    calle_primaria=models.CharField(max_length=150,null=False)
    calle_secundaria=models.CharField(max_length=100,null=False)
    telefono=models.CharField(max_length=13)
    celular=models.CharField(max_length=13)
    estado=models.BooleanField(default=True)

    def __str__(self):
        return self.nombre

#modelo sitio
class Sitio(models.Model):
    sitio_id=models.AutoField(primary_key=True)
    numero=models.CharField(max_length=10,null=False)
    estado=models.BooleanField(default=True)
    Estacionamiento=models.ForeignKey(
        'Estacionamiento',
        on_delete=models.CASCADE,
    )

    def __str__(self):
        return self.numero
        
class Ticket(models.Model):
    ticket_id=models.AutoField(primary_key=True)
    fechaInicio=models.DateField(auto_now =False,auto_now_add=False,null=False)
    fechaFin=models.DateField(auto_now =False,auto_now_add=False,null=False)
    costoHora=models.DecimalField(max_digits=10,decimal_places=3 ,null=False)
    costoTotal=models.DecimalField(max_digits=10,decimal_places=3 ,null=False)
    horaInicio=models.TimeField(auto_now=False, auto_now_add=False)
    horaFin=models.TimeField(auto_now=False, auto_now_add=False)
    Persona=models.ForeignKey(
        'Persona',
        on_delete=models.CASCADE,
    )
    Sitio=models.ForeignKey(
        'Sitio',
        on_delete=models.CASCADE,
    )
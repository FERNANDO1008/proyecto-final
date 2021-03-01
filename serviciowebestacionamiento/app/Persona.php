<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Database\Eloquent\Model;
use Laravel\Lumen\Auth\Authorizable;

class Persona extends Model
{
    protected $table = 'modelo_persona';
    protected $primaryKey = 'persona_id';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'cedula', 'nombres', 'apellidos', 'direccion', 'telefono', 'celular',
    ];

    // Relación
    public function cuenta()
    {
        //relacion de 1 a 1 para traeer la cuenta 
        return $this->hasOne(Cuenta::class,'Persona_id','persona_id');
    }
    public function vehiculo()
    {
        return $this->hasMany(Vehiculo::class,'Persona_id','persona_id');
    }
    public function ticket()
    {
        return $this->belongsTo(Ticket::class);
    }
    public function delete()
    {
        // Borra todos los comentarios 
        foreach ($this->cuenta as $cuenta) {
            $cuenta->delete();
        }

        // Borramos el Post
        return parent::delete();
    }
   public $timestamps = false; //se desacctiva 
    /**
     * The attributes excluded from the model's JSON form.
     *
     * @var array
     */
    // protected $hidden = [
    //     'password',
    // ];
}

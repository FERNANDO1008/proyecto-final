<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Access\Authorizable as AuthorizableContract;
use Illuminate\Contracts\Auth\Authenticatable as AuthenticatableContract;
use Illuminate\Database\Eloquent\Model;
use Laravel\Lumen\Auth\Authorizable;

class Cuenta extends Model
{
    protected $table = 'modelo_cuenta';
    protected $primaryKey = 'cuenta_id';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'correo', 'clave', 'estado',
    ];

    // // Relación
    public function persona()
    {
        return $this->belongsTo(Persona::class);
    }
    // public function delete()
    // {
    //     // Borra todos los comentarios 
    //     foreach ($this->cuenta as $cuenta) {
    //         $cuenta->delete();
    //     }

    //     // Borramos el Post
    //     return parent::delete();
    // }
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

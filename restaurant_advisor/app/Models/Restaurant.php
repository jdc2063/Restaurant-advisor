<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use SoftDeletes;
class Restaurant extends Model
{
    use HasFactory;
    

    static function createDTOtoOBJECT($request) {
        $restaurant = new Restaurant;

        $restaurant->name = $request->name;
        $restaurant->description = $request->description;
        $restaurant->grade = $request->grade;
        $restaurant->localization = $request->localization;
        $restaurant->phone_number = $request->phone_number;
        $restaurant->website = $request->website;
        $restaurant->hours = $request->hours;

        return $restaurant;
    }
    
    static function updateDTOtoOBJECT($request, $restaurant) {

        if ($request->name)    
            $restaurant->name = $request->name;
        if ($request->description)
            $restaurant->description = $request->description;
        if ($request->grade)
            $restaurant->grade = $request->grade;
        if ($request->localization)
            $restaurant->localization = $request->localization;
        if ($request->phone_number)
            $restaurant->phone_number = $request->phone_number;
        if ($request->website)
            $restaurant->website = $request->website;
        if ($request->hours)
            $restaurant->hours = $request->hours;
        return $restaurant;
    }
}

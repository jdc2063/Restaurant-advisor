<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Menu extends Model
{
    use HasFactory;
    
    static function createDTOtoOBJECT($request, $id) {
        $list = Menu::where('restaurant_id', $id)->get();      
        foreach ($list as $menu) {
            if ("$menu->name" == "$request->name") 
                return "400";
        }
        $menu = new Menu;
        $menu->name = $request->name;
        $menu->description = $request->description;
        $menu->price = $request->price;
        $menu->restaurant_id = $id;

        return $menu;
    }

    static function updateDTOtoOBJECT($request, $menu) {
        if ($request->name) {
            $list = Menu::all();
            foreach($list as $menu) {
                if ($menu->name == $request->name)
                    return "400";
            }
            $menu->name = $request->name;
        }
        if ($request->description)
            $menu->description = $request->description;
        if ($request->price)
            $menu->price = $request->price;

        return $menu;
    }
}

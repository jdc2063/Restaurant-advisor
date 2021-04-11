<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Menu;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\SoftDeletes;
use App\Models\Restaurant;
class MenuController extends Controller
{
    function getAll() {
       return Menu::all();
    }

    function getById($id) {
        return Menu::find($id);
    }

    function getAllfromRest($id) {
        return Menu::where('restaurant_id', $id)->get();
    }

    function create(Request $request, $id) {
        $list_r = Restaurant::find($id);
        if ($list_r == null) {
            return response()->json([
                'message' => "Restaurant not exist"
            ], 400);
        }
        $validator = Validator::make($request->all(), [
            'name' => 'required', 
            'description' => 'required',
            'price' => 'required',
        ]);  
        if ($validator->fails()) {
            return response()->json([
                'message' => "A field is missing"
            ], 400);
        } else {
            $menu = Menu::createDTOtoOBJECT($request, $id);
            if ($menu == "400") {
                return response()->json([
                    'message' => "Menu name already exist"
                ], 400);
            }
            $menu->save();
            return response($menu, 201);
        }
    }

    function update(Request $request, $id) {
        $menu = Menu::find($id);
        
        if ($menu) {
            $menu = Menu::updateDTOtoOBJECT($request, $menu);
            if ($menu == "400") {
                return response()->json([
                    'message' => "Menu name already exist."
                ], 400);
            }
            $menu->save();

            return response($menu, 200);
        } else {
            return response()->json([
                'message' => "Menu doesn't exist."
            ], 400); 
        }
    }

    function delete($id) {
        $menu = Menu::find($id);
        if ($menu) {
            $menu->delete();
        } else {
            return response()->json([
                'message' => "Menu doesn't exist."
            ], 400); 
        }
    
    }
}

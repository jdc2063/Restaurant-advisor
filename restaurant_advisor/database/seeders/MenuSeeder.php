<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Str;

class MenuSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('menus')->insert([
            'name' => "Menu Maxi Best Of Big Mac",
            'description' => "Big Mac + boisson + Frites",
            'price' => 10,
            'restaurant_id' => 1,
        ]);
        DB::table('menus')->insert([
            'name' => "Black Coffee",
            'description' => "Black Coffee",
            'price' => 3,
            'restaurant_id' => 2,
        ]);
    }
}

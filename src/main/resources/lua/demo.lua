if (redis.call('exists','spu') == 1) then
   local stock = tonumber(redis.call('get', 'spu'));
   if (stock == -1) then
   return -1;
   end;
   if (stock > 0) then
     redis.call('incrby', 'spu', -1);
     return stock;
   end;
   return 0;
end;
return -1;

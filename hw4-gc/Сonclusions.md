# Сравнение разных сборщиков мусора

## Параметры запуска JVM
```
-Xms256m
-Xmx256m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
```

### G1
-XX:+UseG1GC

### Serial Collector
-XX:+UseSerialGC

### Parallel Collector
-XX:+UseParallelGC

### CMS
-XX:+UseConcMarkSweepGC

### ZGC
-XX:+UnlockExperimentalVMOptions
-XX:+UseZGC

## Результаты

### G1
time:280 sec    
number of iteration completed:1000
number of [G1 Young Generation] is [4265]   
number of [G1 Old Generation] is [27]   
number of [G1 Young Generation] per minute is [913]  
number of [G1 Old Generation] per minute is [5] 

### Serial Collector
time:258 sec    
number of iteration completed:1000
number of [Copy] is [1999]  // minor GC  
number of [MarkSweepCompact] is [999]   // major GC     
number of [Copy] per minute is [464]    // minor GC   
number of [MarkSweepCompact] per minute is [232]    // major GC      

### Parallel Collector
time:495 sec    
number of iteration completed:1000
number of [PS MarkSweep] is [1001]  // major GC     
number of [PS Scavenge] is [3994]   // minor GC     
number of [PS MarkSweep] per minute is [121]  // major GC    
number of [PS Scavenge] per minute is [484]   // minor GC   

### CMS
time:649 sec    
number of iteration completed:1000
number of [ParNew] is [1999]    // minor GC     
number of [ConcurrentMarkSweep] is [1980]   // major GC     
number of [ParNew] per minute is [184]    // minor GC   
number of [ConcurrentMarkSweep] per minute is [183] // major GC     

### ZGC
time:162 sec
number of iteration completed:1000
number of [ZGC] is [1292]   // major GC     
number of [ZGC] per minute is [478] // major GC     

## Выводы. Какой gc лучше и почему
Сравнение сборщиков мусора - сложная задача, и у разных приложений могут быть самые разные потребности.
Среди важных факторов - накладные расходы на производительность (как приложение замедляется сборщиком мусора), 
время остановок, частота и распределение (когда сборщик мусора замораживает приложение, сколько времени требуется для завершения процесса, и как часто это явление происходит) и др.

по итогам замеров можем сделать следующие выводы:
* G1 дает хорошие результаты по скорости работы нашего приложения, превалируют частые очистки молодого поколения
* Serial Collector также показал хорошую производительность приложения, частота очистки молодого и старого поколений сопоставимы
* Parallel Collector для нашего приложения не самый лучший выбор, скорость работы нашего приложения замедлилась вдвое по сравнению с лидерами
* CMS худший по производительности нашего приложения
* ZGC показал лучший результат по производительности нашего приложения, при этом молодое поколение практически не очищалось, что плохо для приложений, где постоянно происходит создание новых объектов в массовом порядке

Отмечу, что для объективности сравнения замеры произведены с учетом одинаковой "полезной работы" приложения.

Таким образом, можно отметить, что для нашего приложения лучшим выбором будет G1 или Serial Collector.
для Java 9+ по-умолчанию используется G1, имеет смысл его и оставить,
так как он менее всего влияет на производительность приложения 280 сек против 495 сек Parallel Collector и 649 сек CMS,
проводит чистку и молодого и старого поколений, в отличие от ZGC.

В целом, имеет смысл менять GC по-умолчанию только в том случае, когда серьезно не устраивает работа текущего. 
Также нужно учитывать размер heap при выборе реализации GC. 
И, безусловно, сначала нужно определить какие цели преследуем, меняя GC.

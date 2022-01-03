package net.kiranatos;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service //аннотация помечает бин как сервис
public class InitiateUtils implements CommandLineRunner { //имплементируем интерфейс CommandLineRunner (командная строка запуска)

    private final FruitService fruitService;

    public InitiateUtils(FruitService fruitService) {//незабываем конструктор для внедрения
        this.fruitService = fruitService;
    }

    @Override
    //переопределяем метод который позволит
//нам выполнять методы нашего приложения при запуске
    public void run(String... args) throws Exception {
        System.out.println("<<<RUN>>>"); //проверим что это работает

//создаем несколько сущностей фруктов, через сеттеры заполняем поля
        FruitEntity fruitEntity1 = new FruitEntity();
        fruitEntity1.setFruitName("fruit1");
        fruitEntity1.setProviderCode(1);

        FruitEntity fruitEntity2 = new FruitEntity();
        fruitEntity2.setFruitName("fruit2");
        fruitEntity2.setProviderCode(2);

        FruitEntity fruitEntity3 = new FruitEntity();
        fruitEntity3.setFruitName("fruit3");
        fruitEntity3.setProviderCode(3);

//с помощью переменной сервиса вызываем методы сохранения в базу, по разу для одного объекта
        fruitService.save(fruitEntity1);
        fruitService.save(fruitEntity2);
        fruitService.save(fruitEntity3);

//здесь вытаскиваем базу обратно
        List<FruitEntity> all = fruitService.getAll();

//и выводим что получилось
        for (FruitEntity entity : all) {
            System.out.println(entity);
        }
    }
}

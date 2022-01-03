package net.kiranatos;

import java.util.List;
import org.springframework.stereotype.Service;

@Service//помечаем что этот бин - сервис
public class FruitService {

    private final FruitRepository fruitRepository;  //final переменная репозитория

    public FruitService(FruitRepository fruitRepository) {//внедрили зависимость через конструктор
        this.fruitRepository = fruitRepository;
    }
//создали публичный метод (название любое может быть)
//на вход принимает сущность и сохраняет ее в базу

    public void save(FruitEntity fruitEntity) {
        fruitRepository.save(fruitEntity); //реализовали метод внедренного бина
    }

//возвращает лист всех сущностей из базы
    public List<FruitEntity> getAll() {
        return fruitRepository.findAll(); //реализовали метод внедренного бина
    }

}

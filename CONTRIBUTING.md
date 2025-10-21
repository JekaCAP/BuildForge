# Создание ветки для задачи

Каждая задача из Kanban-доски **BuildForge** должна иметь свою отдельную ветку. Следуйте этой инструкции, чтобы правильно создавать ветки и привязывать их к Issue.

---

## 1. Проверка ветки `develop`

Перед созданием feature-ветки убедитесь, что вы находитесь на ветке `develop` и она актуальна.

```bash
git checkout develop
git pull origin develop
```
---

## 2. Создание ветки 

Ветки называем по стандарту, в зависимости от текущей задачи. 

Смотри на лейбл задачи. Например "feature"

Значит от ветки develop создаём ветку типа:

```
feature/<номер_задачи>-<короткое_описание>

feature/JTFP-1
feature/JTFP-1-user-entity
```

---

### 3. Работа над задачей

1. **Делайте коммиты локально**

```bash
git add .
git commit -m "JTFP-1: добавить сущность User"
git add . — добавляет все изменения в staging area
```

git commit -m "<номер_задачи>: <короткое описание>" — создаёт коммит с номером задачи для привязки к Issue

Пушьте ветку на удалённый репозиторий

git push -u origin feature/JTFP-1
-u origin <branch> устанавливает upstream, чтобы в будущем можно было просто делать git push и git pull без указания ветки.

---

### 4. Создание Pull Request

1. Перейдите на GitHub в репозиторий **BuildForge**.
2. GitHub предложит создать PR из вашей ветки (`feature/JTFP-1`).
3. В описании PR обязательно укажите привязку к Issue:


> GitHub автоматически закроет Issue при мерже PR.

---

### 5. Дальнейший workflow

- После создания PR карточка на Kanban-доске автоматически перемещается по колонкам:

InProgress → OnReview → ReadyForMerge → Done

```bash
# Переключаемся на develop
git checkout develop

# Обновляем локальную develop
git pull origin develop

```


## Настройка Cloudinary
1. Создайте файл `Secretiki` в `app/src/main/assets`
2. Добавьте следующие ключи:
CLOUDINARY_CLOUD_NAME=ваш_ключ
CLOUDINARY_API_KEY=ваш_ключ
CLOUDINARY_API_SECRET=ваш_ключ
4. добавьте файл в gitignore.

## Настройка Firebase
1. Этот проект использует Firebase. Для сборки требуется файл `google-services.json`.
2. Перейдите в [Firebase Console](https://console.firebase.google.com/), создайте проект и скачайте файл `google-services.json`.
3. Создайте базу данных и коллекции "Users", "Projects", "Chats" 
4. Поместите `google-services.json` в папку `app/`
5. Добавьте файл в gitignore.

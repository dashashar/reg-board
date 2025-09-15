$(document).ready(function() {
    const $form = $('#form');
    const $timeStartInput = $('#timeStart');
    const $timeEndInput = $('#timeEnd');
    const $dateError = $('#dateError');

    $timeStartInput.add($timeEndInput).on('change', validateDates);

    function validateDates() {
        const startValue = $timeStartInput.val();
        const endValue = $timeEndInput.val();

        if (!startValue || !endValue) {
            showDateError('Оба поля даты должны быть заполнены');
            return false;
        }

        const startDate = new Date(startValue);
        const endDate = new Date(endValue);

        if (startDate >= endDate) {
            showDateError('Дата окончания должна быть позже даты начала');
            return false;
        }

        $dateError.hide();
        return true;
    }

    function showDateError(message) {
        $dateError.text(message).show();
    }

    const $imageUpload = $('#imageUpload');
    const $imageIdInput = $('#imgId');
    const $currentImageContainer = $('#currentImageContainer');
    const $changeImageBtn = $('#changeImageBtn');
    const $imagePreview = $('#imagePreview');
    let currentFile = null;

    if ($changeImageBtn.length) {
        $changeImageBtn.on('click', handleChangeImageClick);
    }

    $imageUpload.on('change', handleImageUpload);

    function handleChangeImageClick() {
        $currentImageContainer.hide();
        $imageUpload.show().val('');
    }

    function handleImageUpload(e) {
        const file = e.target.files[0];
        if (!file) return;

        currentFile = file;
        previewImage(file);
    }

    function previewImage(file) {
        const reader = new FileReader();
        reader.onload = function(event) {
            $imagePreview.html(`
                <img src="${event.target.result}" 
                     alt="Preview" 
                     style="max-width: 200px;">
                <button type="button" id="cancelUploadBtn">Отмена</button>
            `);

            $('#cancelUploadBtn').on('click', handleCancelUpload);
        };
        reader.readAsDataURL(file);
    }

    function handleCancelUpload() {
        $imagePreview.empty();
        $imageUpload.val('');
        currentFile = null;
        if ($imageIdInput.val()) {
            $currentImageContainer.show();
            $imageUpload.hide();
        }
    }

    $form.on('submit', function(event) {
        event.preventDefault();

        if (!validateDates()) {
            return false;
        }

        handleFormSubmit(event);
    });

    async function handleFormSubmit(e) {
        const form = e.target;
        const formData = new FormData(form);

        try {
            if (currentFile) {
                await processImageUpload(formData);
            }

            await submitFormData(form, formData);
        } catch (error) {
            console.error('Form submission error:', error);
            showDateError('Ошибка отправки формы');
        }
    }

    async function processImageUpload(formData) {
        const originalImageId = $imageIdInput.val();

        try {
            if (originalImageId) {
                await deleteImage(originalImageId);
            }

            const imgId = await uploadImage(currentFile);
            $imageIdInput.val(imgId);
            formData.set('imgId', imgId);
        } catch (error) {
            console.error('Upload error:', error);
            showDateError('Ошибка загрузки изображения');
            throw error;
        }
    }

    async function deleteImage(imageId) {
        return $.ajax({
            url: `/api/image/${imageId}`,
            method: 'DELETE'
        });
    }

    async function uploadImage(file) {
        const uploadFormData = new FormData();
        uploadFormData.append('file', file);

        return $.ajax({
            url: '/api/image/upload',
            method: 'POST',
            data: uploadFormData,
            processData: false,
            contentType: false
        }).then(response => response.imgId);
    }

    async function submitFormData(form, formData) {
        form.submit();
    }

    function getCsrfToken() {
        return $('input[name="_csrf"]').val() || getCookie('XSRF-TOKEN');
    }

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }
});
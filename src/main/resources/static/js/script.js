function previewImages(event, previewContainerId) {
        const container = document.getElementById(previewContainerId);
        container.innerHTML = ""; // clear old previews
        const files = event.target.files;

        Array.from(files).forEach(file => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = document.createElement("img");
                img.src = e.target.result;
                img.className = "thumb";
                container.appendChild(img);
            }
            reader.readAsDataURL(file);
        });
    }
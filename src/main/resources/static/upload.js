const host = "http://localhost:8080"

document.getElementById('file').addEventListener('change', function (event) {
    const file = event.target.files[0];
    const fileInfo = document.getElementById('fileInfo');

    if (file) {
        fileInfo.innerHTML = `
            <p>File Name: ${file.name}</p>
            <p>File Size: ${(file.size / 1024).toFixed(2)} KB</p>
            <p>File Type: ${file.type}</p>
        `;
    } else {
        fileInfo.innerHTML = '<p>No file selected</p>';
    }
});

document.getElementById('uploadForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the form from submitting traditionally

    const userName = document.getElementById('userName').value;
    const fileInput = document.getElementById('file');
    const file = fileInput.files[0];

    if (file) {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('filename', file.name);

        fetch(`${host}/directories/${userName}/files`, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok || response.status === 201) {
                    alert('파일 업로드에 성공했습니다.');
                    window.location.reload()
                } else {
                    alert('파일 업로드에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error uploading file:', error);
                alert('An error occurred while uploading the file.');
            });
    } else {
        alert('No file selected.');
    }
});

const addFileClickEvents = () => document.querySelectorAll('.folder').forEach(folder => {
    const folderName = folder.innerText
    const files = folder.parentElement.querySelectorAll('.file');
    const deleteBtns = folder.parentElement.querySelectorAll('.deleteBtn');
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const deleteBtn = deleteBtns[i];

        const filename = file.innerText
        file.addEventListener('click', event => {
            if (filename.endsWith('.png') || filename.endsWith('.jpg') || filename.endsWith('.jpeg')) {
                const resourceUrl = `${host}/directories/${folderName}/files/${filename}`
                getResource(resourceUrl)
            } else {
                const requestUrl = `${host}/directories/${folderName}/files/${filename}/download`
                downloadResource(requestUrl)
            }
        });

        deleteBtn.addEventListener('click', event => {
            const requestUrl = `${host}/directories/${folderName}/files/${filename}`
            deleteResource(requestUrl)
        });
    }
});

const getResource = (resourceUrl) => {
    const img = document.createElement('img')
    img.src = resourceUrl

    const content = document.getElementById('content')
    content.innerHTML = ''
    content.appendChild(img)
}

const downloadResource = (requestUrl) => {
    const iframe = document.createElement('iframe')
    iframe.style = 'display:none;';
    iframe.src = requestUrl

    const content = document.getElementById('content')
    content.innerHTML = ''
    content.appendChild(iframe)
}

const deleteResource = (requestUrl) => {
    fetch(requestUrl, {method: 'DELETE'})
        .then(response => {
            if (response.ok || response.status !== 204) {
                alert('파일 삭제 성공했습니다.');
                window.location.reload()
            } else {
                alert('파일 삭제에 실제했습니다.');
            }
        })
}

window.onload = () => {
    const rootDirectory = document.getElementById("directory");
    allFilesWithDirectory(rootDirectory)
        .then(() => addFileClickEvents())
}

const allFilesWithDirectory = (rootDirectory) => {
    return fetch(`${host}/directories`)
        .then(response => {
            if (response.ok) {
                return response.json()
            }
        })
        .then(json => {
            rootDirectory.appendChild(generateFileStructure(json));
        })
}

const generateFileStructure = (data) => {
    const list = document.createElement('li');
    const root = document.createElement('h3');
    root.className = 'root';
    root.textContent = 'Storage';
    const ul = document.createElement('ul');

    data.forEach((userFiles, index) => {
        if (userFiles.length === 0) return; // Skip empty arrays

        const userLi = document.createElement('li');
        const userSpan = document.createElement('span')
        userSpan.className = 'folder';
        userSpan.textContent = userFiles[0].userName // Label for each group
        userLi.appendChild(userSpan)

        const filesUl = document.createElement('ul');
        userFiles.forEach(file => {
            const fileSpan = document.createElement('span')
            fileSpan.className = 'file';
            fileSpan.textContent = file.filename;

            const fileDeleteButton = document.createElement('button')
            fileDeleteButton.className = 'deleteBtn'
            fileDeleteButton.textContent = '삭제'

            const fileLi = document.createElement('li');
            fileLi.appendChild(fileSpan)
            fileLi.appendChild(fileDeleteButton)
            filesUl.appendChild(fileLi);
        });

        userLi.appendChild(filesUl);
        ul.appendChild(userLi);
    });

    list.appendChild(root)
    list.appendChild(ul)

    return list;
}
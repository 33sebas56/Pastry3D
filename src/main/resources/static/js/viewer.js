const container = document.getElementById("viewer-container");
const form = document.getElementById("dessert-form");
const promptInput = document.getElementById("prompt");
const statusDiv = document.getElementById("status");
const dessertTitle = document.getElementById("dessert-title");
const dessertDescription = document.getElementById("dessert-description");
const ingredientsList = document.getElementById("ingredients-list");
const stepsList = document.getElementById("steps-list");
const resetBtn = document.getElementById("reset-btn");

const scene = new THREE.Scene();

const camera = new THREE.PerspectiveCamera(
    60,
    container.clientWidth / container.clientHeight,
    0.1,
    1000
);

const renderer = new THREE.WebGLRenderer({ antialias: true });
renderer.setSize(container.clientWidth, container.clientHeight);
renderer.setPixelRatio(window.devicePixelRatio);
container.appendChild(renderer.domElement);

camera.position.set(0, 6, 14);
camera.lookAt(0, 3, 0);

const ambientLight = new THREE.AmbientLight(0xffffff, 1.2);
scene.add(ambientLight);

const directionalLight = new THREE.DirectionalLight(0xffffff, 1.5);
directionalLight.position.set(6, 12, 8);
scene.add(directionalLight);

const backLight = new THREE.DirectionalLight(0xffffff, 0.6);
backLight.position.set(-8, 6, -6);
scene.add(backLight);

const floorGeometry = new THREE.CircleGeometry(8, 64);
const floorMaterial = new THREE.MeshStandardMaterial({
    color: "#1f2937",
    roughness: 0.9,
    metalness: 0.05
});
const floor = new THREE.Mesh(floorGeometry, floorMaterial);
floor.rotation.x = -Math.PI / 2;
floor.position.y = -0.01;
scene.add(floor);

const dessertGroup = new THREE.Group();
scene.add(dessertGroup);

function clearDessertScene() {
    while (dessertGroup.children.length > 0) {
        const child = dessertGroup.children[0];
        dessertGroup.remove(child);

        if (child.geometry) {
            child.geometry.dispose();
        }

        if (child.material) {
            child.material.dispose();
        }
    }
}

function createMaterial(color) {
    return new THREE.MeshStandardMaterial({
        color: color || "#d6d3d1",
        roughness: 0.75,
        metalness: 0.05
    });
}

function renderSceneObjects(objects) {
    clearDessertScene();

    if (!objects || objects.length === 0) {
        return;
    }

    objects.forEach(obj => {
        let mesh = null;

        if (obj.type === "cylinder") {
            const geometry = new THREE.CylinderGeometry(
                obj.radiusTop,
                obj.radiusBottom,
                obj.height,
                48
            );
            mesh = new THREE.Mesh(geometry, createMaterial(obj.color));
        }

        if (obj.type === "sphere") {
            const geometry = new THREE.SphereGeometry(obj.radius, 32, 32);
            mesh = new THREE.Mesh(geometry, createMaterial(obj.color));
        }

        if (obj.type === "box") {
            const geometry = new THREE.BoxGeometry(obj.width, obj.height, obj.depth);
            mesh = new THREE.Mesh(geometry, createMaterial(obj.color));
        }

        if (obj.type === "torus") {
            const geometry = new THREE.TorusGeometry(obj.radius, obj.tube, 24, 64);
            mesh = new THREE.Mesh(geometry, createMaterial(obj.color));
        }

        if (obj.type === "cone") {
            const geometry = new THREE.ConeGeometry(obj.radius, obj.height, 32);
            mesh = new THREE.Mesh(geometry, createMaterial(obj.color));
        }

        if (mesh) {
            mesh.position.set(
                obj.positionX || 0,
                obj.positionY || 0,
                obj.positionZ || 0
            );

            if (obj.rotationX !== undefined) {
                mesh.rotation.x = obj.rotationX;
            }
            if (obj.rotationY !== undefined) {
                mesh.rotation.y = obj.rotationY;
            }
            if (obj.rotationZ !== undefined) {
                mesh.rotation.z = obj.rotationZ;
            }

            dessertGroup.add(mesh);
        }
    });
}

function renderInfo(data) {
    dessertTitle.textContent = data.dessertName || "Resultado";
    dessertDescription.textContent = data.description || "Sin descripción.";

    ingredientsList.innerHTML = "";
    stepsList.innerHTML = "";

    if (data.ingredients && data.ingredients.length > 0) {
        data.ingredients.forEach(item => {
            const li = document.createElement("li");
            li.textContent = item;
            ingredientsList.appendChild(li);
        });
    }

    if (data.steps && data.steps.length > 0) {
        data.steps.forEach(step => {
            const li = document.createElement("li");
            li.textContent = step;
            stepsList.appendChild(li);
        });
    }
}

async function generateDessert(promptText) {
    statusDiv.textContent = "Generando postre...";

    try {
        const response = await fetch("/api/desserts/generate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                prompt: promptText
            })
        });

        if (!response.ok) {
            throw new Error("No se pudo generar el postre");
        }

        const data = await response.json();

        renderInfo(data);

        if (data.scene && data.scene.objects) {
            renderSceneObjects(data.scene.objects);
        }

        statusDiv.textContent = "Postre generado correctamente.";
    } catch (error) {
        console.error(error);
        statusDiv.textContent = "Ocurrió un error al generar el postre.";
    }
}

function animate() {
    requestAnimationFrame(animate);
    dessertGroup.rotation.y += 0.005;
    renderer.render(scene, camera);
}
animate();

window.addEventListener("resize", () => {
    const width = container.clientWidth;
    const height = container.clientHeight;

    camera.aspect = width / height;
    camera.updateProjectionMatrix();
    renderer.setSize(width, height);
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    await generateDessert(promptInput.value.trim());
});

resetBtn.addEventListener("click", () => {
    clearDessertScene();
    dessertTitle.textContent = "Resultado";
    dessertDescription.textContent = "Aquí aparecerá la descripción generada por el backend.";
    ingredientsList.innerHTML = "";
    stepsList.innerHTML = "";
    statusDiv.textContent = "Escena limpiada.";
});

generateDessert(promptInput.value.trim());
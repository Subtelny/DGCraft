package pl.subtelny.crate.api.prototype;

import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.ItemCrate;
import pl.subtelny.crate.api.creator.CrateCreatorRequest;
import pl.subtelny.crate.api.type.basic.creator.BasicCrateCreatorRequest;

import java.util.Map;
import java.util.Objects;

public class CratePrototype {

    private final CrateKey crateKey;

    private final CrateType crateType;

    private final String title;

    private final String permission;

    private final int size;

    private final Map<Integer, ItemCrate> content;

    public CratePrototype(CrateKey crateKey, CrateType crateType, String title, String permission, int size, Map<Integer, ItemCrate> content) {
        this.crateKey = crateKey;
        this.crateType = crateType;
        this.title = title;
        this.permission = permission;
        this.size = size;
        this.content = content;
    }

    public CrateKey getCrateKey() {
        return crateKey;
    }

    public CrateType getCrateType() {
        return crateType;
    }

    public String getTitle() {
        return title;
    }

    public String getPermission() {
        return permission;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, ItemCrate> getContent() {
        return content;
    }

    public CrateCreatorRequest toCrateCreatorRequest() {
        return BasicCrateCreatorRequest.builder(crateKey, size)
                .setTitle(title)
                .setPermission(permission)
                .setContent(content)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CratePrototype that = (CratePrototype) o;
        return size == that.size && Objects.equals(crateKey, that.crateKey) && Objects.equals(crateType, that.crateType) && Objects.equals(title, that.title) && Objects.equals(permission, that.permission) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crateKey, crateType, title, permission, size, content);
    }
}

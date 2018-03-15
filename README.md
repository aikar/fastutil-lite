# fastutil-lite
This is a bare minimal copy of [fastutil](https://github.com/vigna/fastutil)

This only includes selective int and long collections, targeting the hashmaps.

## Repo
Maven:
```xml
<repository>
    <id>aikar</id>
    <url>https://repo.aikar.co/content/groups/aikar/</url>
</repository>
```
Gradle:
```gradle
maven {
    name = "aikar"
    url = "https://repo.aikar.co/content/groups/aikar/"
}
```

## v2 - about 80kb~ depending on number of collections
there are 4 primary collections exposed.

**group**: `co.aikar`
**version**: `2.0-SNAPSHOT`

All of these collections need:
**artifact**: `fastutil-base`

Int Hashmaps need:
**artifact**: `fastutil-intbase`

Long Hashmaps need:
**artifact**: `fastutil-intbase`

**Int2ObjectOpenHashMap**: `fastutil-inthashmap`
**Int2ObjectOpenCustomHashMap**: `fastutil-intcustomhashmap`
**Long2ObjectOpenHashMap**: `fastutil-longhashmap`
**Long2ObjectOpenCustomHashMap**: `fastutil-longcustomhashmap`

You should have at least 3 artifacts, `core`, `intbase` or `longbase` (or both), and then one or more of the desired collections.

Example:
```xml
    <dependencies>
        <dependency>
            <groupId>co.aikar</groupId>
            <artifactId>fastutil-base</artifactId>
            <version>2.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>co.aikar</groupId>
            <artifactId>fastutil-longbase</artifactId>
            <version>2.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>co.aikar</groupId>
            <artifactId>fastutil-longbase</artifactId>
            <version>2.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

Relocating should not be necessary for this since ABI is unlikely to change. I am using snapshots until I feel the build is 'correct' and works, then it will go to a release.

## v1 - about 500kb
for v1 of fastutil-lite that contains most of the int/long classes, use:

**group**: `co.aikar`
**artifact**: `fastutil-lite`
**version**: `1.0`

# License
fastutil is released under the [Apache v2 license](LICENSE.md)

fastutil-lite does not change the implementation details of fastutil.

At most, my changes have removed some methods to trim dependencies.

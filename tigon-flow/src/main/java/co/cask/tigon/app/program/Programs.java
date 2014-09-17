/*
 * Copyright 2014 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package co.cask.tigon.app.program;

import com.google.common.base.Objects;
import org.apache.twill.filesystem.Location;

import java.io.File;
import java.io.IOException;

/**
 * Factory helper to create {@link Program}.
 */
public final class Programs {

  public static Program createWithUnpack(Location location, File destinationUnpackedJarDir,
                                         ClassLoader parentClassLoader) throws IOException {
    return new DefaultProgram(location, destinationUnpackedJarDir, parentClassLoader);
  }

  public static Program createWithUnpack(Location location, File destinationUnpackedJarDir) throws IOException {
    return Programs.createWithUnpack(location, destinationUnpackedJarDir, getClassLoader());
  }

  /**
   * Creates a {@link Program} without expanding the location jar. The {@link Program#getClassLoader()}
   * would not function from the program this method returns.
   */
  public static Program create(Location location, ClassLoader classLoader) throws IOException {
    return new DefaultProgram(location, classLoader);
  }

  public static Program create(Location location) throws IOException {
    return new DefaultProgram(location, getClassLoader());
  }

//  /**
//   * Get program location
//   *
//   * @param factory  location factory
//   * @param filePath app fabric output directory path
//   * @param id       program id
//   * @param type     type of the program
//   * @return         Location corresponding to the program id
//   * @throws java.io.IOException incase of errors
//   */
//  public static Location programLocation(LocationFactory factory, String filePath, Id.Program id, ProgramType type)
//                                         throws IOException {
//    Location allAppsLocation = factory.create(filePath);
//
//    Location accountAppsLocation = allAppsLocation.append(id.getAccountId());
//    String name = String.format(Locale.ENGLISH, "%s/%s", type.toString(), id.getApplicationId());
//    Location applicationProgramsLocation = accountAppsLocation.append(name);
//    if (!applicationProgramsLocation.exists()) {
//      throw new FileNotFoundException("Unable to locate the Program,  location doesn't exist: "
//                                   + applicationProgramsLocation.toURI().getPath());
//    }
//    Location programLocation = applicationProgramsLocation.append(String.format("%s.jar", id.getId()));
//    if (!programLocation.exists()) {
//      throw new FileNotFoundException(String.format("Program %s.%s of type %s does not exists.",
//                                               id.getApplication(), id.getId(), type));
//    }
//    return programLocation;
//  }

  private static ClassLoader getClassLoader() {
    return Objects.firstNonNull(Thread.currentThread().getContextClassLoader(), Programs.class.getClassLoader());
  }

  private Programs() {
  }
}